package by.tasks.application.domain.account;

import by.tasks.application.BankApplicationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static by.tasks.application.command.Command.CommandType.BANK_ADD_CUSTOMER;

public class AccountService {

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> getCustomerAccounts(UUID customerId) {
        return accountDao.getCustomerAccounts(customerId);
    }

    public Account addAccount(UUID bankId, UUID customerId, Currency currency, BigDecimal balance) {
        if (!accountDao.existsByBankAndCustomer(bankId, customerId)) {
            throw new BankApplicationException("Customer should already have an account for this bank - use " + BANK_ADD_CUSTOMER + " command first");
        }
        return accountDao.save(new Account(customerId, bankId, currency, balance));
    }
}
