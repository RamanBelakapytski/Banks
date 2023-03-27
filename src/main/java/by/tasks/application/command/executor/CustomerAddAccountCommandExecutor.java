package by.tasks.application.command.executor;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.account.AccountService;
import by.tasks.application.domain.account.Currency;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CustomerAddAccountCommandExecutor implements CommandExecutor {

    private final AccountService accountService;

    public CustomerAddAccountCommandExecutor(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute(List<String> params) {
        if (params.size() != 4) {
            throw new BankApplicationException("Invalid parameters count");
        }
        UUID customerId;
        UUID bankId;
        Currency currency;
        BigDecimal balance;

        try {
            bankId = UUID.fromString(params.get(0));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for BANK_ID");
        }

        try {
            customerId = UUID.fromString(params.get(1));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for CUSTOMER_ID");
        }

        try {
            currency = Currency.valueOf(params.get(2));
        } catch (IllegalArgumentException e) {
            throw new BankApplicationException("Invalid format for CURRENCY, valid values: " + Arrays.toString(Currency.values()));
        }

        try {
            balance = new BigDecimal(params.get(3));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for BALANCE");
        }

        System.out.println("Account created : " + accountService.addAccount(bankId, customerId, currency, balance));
    }
}
