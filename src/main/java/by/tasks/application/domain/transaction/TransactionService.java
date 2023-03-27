package by.tasks.application.domain.transaction;

import by.tasks.application.BankApplicationException;
import by.tasks.application.database.Database;
import by.tasks.application.domain.account.AccountDao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionService {
    private final Database database;
    private final AccountDao accountDao;
    private final FeeCalculator feeCalculator;
    private final TransactionDao transactionDao;

    public TransactionService(Database database, AccountDao accountDao, FeeCalculator feeCalculator, TransactionDao transactionDao) {
        this.database = database;
        this.feeCalculator = feeCalculator;
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    public Transaction performTransaction(UUID accountFrom, UUID accountTo, BigDecimal amount) {
        final var connection = database.getConnection();
        database.setManualTransactionManagement(true);
        Transaction transaction = null;

        try {
            var accFrom = accountDao.findById(accountFrom).orElseThrow();
            var accTo = accountDao.findById(accountTo).orElseThrow();

            if (accFrom.getCurrency() != accTo.getCurrency()) {
                throw new BankApplicationException("Money transfer may be done only between accounts with same currency");
            }

            var fee = feeCalculator.calculateFee(accFrom, accTo);

            if (accFrom.getBalance().compareTo(amount.add(fee)) < 0) {
                throw new BankApplicationException("Нужно больше золота :)");
            }

            accFrom.setBalance(accFrom.getBalance().subtract(amount).subtract(fee));
            accTo.setBalance(accTo.getBalance().add(amount));

            transaction = new Transaction(amount, fee, LocalDateTime.now(), accFrom.getCurrency(), accountFrom, accountTo);

            accountDao.save(accFrom);
            accountDao.save(accTo);
            transactionDao.save(transaction);

            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } finally {
            database.setManualTransactionManagement(false);
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
        return transaction;
    }
}
