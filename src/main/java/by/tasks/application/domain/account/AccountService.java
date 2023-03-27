package by.tasks.application.domain.account;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.bank.BankDao;
import by.tasks.application.domain.customer.CustomerDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static by.tasks.application.command.Command.CommandType.BANKS_ADD_CUSTOMER;

public class AccountService {

    private final AccountDao accountDao;
    private final BankDao bankDao;
    private final CustomerDao customerDao;

    public AccountService(AccountDao accountDao, BankDao bankDao, CustomerDao customerDao) {
        this.accountDao = accountDao;
        this.bankDao = bankDao;
        this.customerDao = customerDao;
    }

    public List<Account> getCustomerAccounts(UUID customerId) {
        return accountDao.getCustomerAccounts(customerId);
    }

    public Account addAccount(UUID bankId, UUID customerId, Currency currency, BigDecimal balance) {
        if (!accountDao.existsByBankAndCustomer(bankId, customerId)) {
            throw new BankApplicationException("Customer should already have an account for this bank - use " + BANKS_ADD_CUSTOMER + " command first");
        }
        return accountDao.save(new Account(customerId, bankId, currency, balance));
    }
}
