package by.tasks.application.command.executor;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.transaction.TransactionService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CustomerGetTransactionsCommandExecutor implements CommandExecutor {

    private final TransactionService transactionService;

    public CustomerGetTransactionsCommandExecutor(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void execute(List<String> params) {
        if (params.size() != 3) {
            throw new BankApplicationException("Invalid parameters count");
        }

        UUID customerId;
        LocalDate dateFrom;
        LocalDate dateTo;

        try {
            customerId = UUID.fromString(params.get(0));
        } catch (IllegalArgumentException e) {
            throw new BankApplicationException("Invalid value for CUSTOMER_ID");
        }

        try {
            dateFrom = LocalDate.parse(params.get(1));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid value for DATE_FROM - expected format is ISO_LOCAL_DATE (yyyy-mm-dd)");
        }

        try {
            dateTo = LocalDate.parse(params.get(2));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid value for DATE_TO - expected format is ISO_LOCAL_DATE (yyyy-mm-dd)");
        }

        System.out.println("Customer transactions: " + transactionService.findByCustomerId(customerId, dateFrom, dateTo));
    }
}
