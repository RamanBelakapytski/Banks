package by.tasks.application.domain.transaction;

import by.tasks.application.database.Database;
import by.tasks.application.domain.account.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionDao {

    private final Database database;

    public TransactionDao(Database database) {
        this.database = database;
    }

    public Transaction save(Transaction transaction) {
        if (transaction.getId() == null) {
            transaction.setId(UUID.randomUUID());
            try (var connection = database.getConnection();
                 var statement = connection.prepareStatement("INSERT INTO transaction (id, account_from_id, account_to_id, currency, amount, fee, datetime) VALUES (?, ?, ?, ?, ?, ?, ?)")
            ) {
                statement.setObject(1, transaction.getId());
                statement.setObject(2, transaction.getAccountFrom());
                statement.setObject(3, transaction.getAccountTo());
                statement.setString(4, transaction.getCurrency().name());
                statement.setBigDecimal(5, transaction.getAmount());
                statement.setBigDecimal(6, transaction.getFee());
                statement.setTimestamp(7, Timestamp.valueOf(transaction.getDateTime()));
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            try (var connection = database.getConnection();
                 var statement = connection.prepareStatement("UPDATE transaction SET account_from_id = ?, account_to_id = ?, currency = ?, amount = ?, fee = ?, datetime = ? WHERE id = ?")
            ) {
                statement.setObject(1, transaction.getId());
                statement.setObject(2, transaction.getAccountFrom());
                statement.setObject(3, transaction.getAccountTo());
                statement.setString(4, transaction.getCurrency().name());
                statement.setBigDecimal(5, transaction.getAmount());
                statement.setBigDecimal(6, transaction.getFee());
                statement.setTimestamp(7, Timestamp.valueOf(transaction.getDateTime()));
                statement.setObject(8, transaction.getId());
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return transaction;
    }

    public List<Transaction> findByCustomerId(UUID customerId, LocalDate from, LocalDate to) {
        final var arrayList = new ArrayList<Transaction>();

        try (var connection = database.getConnection();
             var statement = connection.prepareStatement("""
                     SELECT t.id, t.account_from_id, t.account_to_id, t.currency, t.amount, t.fee, t.datetime
                     FROM transaction t INNER JOIN account a ON t.account_from_id = a.id OR t.account_to_id = a.id
                     WHERE a.customer_id = ?
                     """)
        ) {
            statement.setObject(1, customerId);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    arrayList.add(toTransaction(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    private Transaction toTransaction(ResultSet resultSet) throws SQLException {
        return new Transaction(
                (UUID) resultSet.getObject("id"),
                resultSet.getBigDecimal("amount"),
                resultSet.getBigDecimal("fee"),
                resultSet.getTimestamp("datetime").toLocalDateTime(),
                Currency.valueOf(resultSet.getString("currency")),
                (UUID) resultSet.getObject("account_from_id"),
                (UUID) resultSet.getObject("account_to_id")
        );
    }
}
