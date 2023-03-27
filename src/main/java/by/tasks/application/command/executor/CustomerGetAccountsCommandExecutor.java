package by.tasks.application.command.executor;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.account.AccountService;

import java.util.List;
import java.util.UUID;

public class CustomerGetAccountsCommandExecutor implements CommandExecutor {

    private final AccountService accountService;

    public CustomerGetAccountsCommandExecutor(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute(List<String> params) {
        if (params.size() != 1) {
            throw new BankApplicationException("Invalid parameters count");
        }
        UUID customerId;
        try {
            customerId = UUID.fromString(params.get(0));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for CUSTOMER_ID");
        }
        System.out.println("Customer accounts: " + accountService.getCustomerAccounts(customerId));
    }
}
