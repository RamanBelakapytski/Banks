package by.tasks.application.command.executor;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.transaction.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TransactionPerformCommandExecutor implements CommandExecutor {

    private final TransactionService transactionService;

    public TransactionPerformCommandExecutor(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void execute(List<String> params) {
        if (params.size() != 3) {
            throw new BankApplicationException("Invalid parameters count");
        }

        UUID accountFrom;
        UUID accountTo;
        BigDecimal amount;

        try {
            accountFrom = UUID.fromString(params.get(0));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for ACCOUNT_FROM_ID");
        }

        try {
            accountTo = UUID.fromString(params.get(1));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for ACCOUNT_TO_ID");
        }

        try {
            amount = new BigDecimal(params.get(2));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for AMOUNT");
        }


        System.out.println("New transaction: " + transactionService.performTransaction(accountFrom, accountTo, amount));
    }
}
