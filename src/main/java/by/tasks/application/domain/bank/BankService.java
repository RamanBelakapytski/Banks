package by.tasks.application.domain.bank;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.account.Account;
import by.tasks.application.domain.account.AccountDao;
import by.tasks.application.domain.account.Currency;
import by.tasks.application.domain.customer.Customer;
import by.tasks.application.domain.customer.CustomerDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class BankService {
    private final BankDao bankDao;
    private final static Currency DEFAULT_CURRENCY = Currency.BYN;
    private final static BigDecimal INITIAL_BALANCE = new BigDecimal("10000");
    private final CustomerDao customerDao;
    private final AccountDao accountDao;

    public BankService(BankDao bankDao, CustomerDao customerDao, AccountDao accountDao) {
        this.bankDao = bankDao;
        this.customerDao = customerDao;
        this.accountDao = accountDao;
    }

    public Bank addNewBank(String name, BigDecimal legalFee, BigDecimal naturalFee) {
        if (bankDao.existsByName(name)) {
            throw new BankApplicationException("Bank with name `" + name + "` already exists");
        }
        return bankDao.save(new Bank(name, legalFee, naturalFee));
    }

    public Bank updateBank(UUID id, BigDecimal legalFee, BigDecimal naturalFee) {
        var bank = findBankById(id);

        bank.setFee(legalFee, naturalFee);

        return bankDao.save(bank);
    }

    public Account addCustomer(UUID bankId, UUID customerId) {
        var bank = findBankById(bankId);
        var customer = findCustomerById(customerId);

        if (accountDao.existsByBankAndCustomer(bankId, customerId)) {
            throw new BankApplicationException("Customer " + customerId + " already has account in bank " + bankId);
        }

        var defaultAccount = new Account(customerId, bankId, DEFAULT_CURRENCY, INITIAL_BALANCE);

        return accountDao.save(defaultAccount);
    }

    public List<Bank> findAll() {
        return bankDao.findAll();
    }

    private Bank findBankById(UUID id) {
        return bankDao.findById(id).orElseThrow(() -> new BankApplicationException("Bank with id = " + id + " not found"));
    }

    private Customer findCustomerById(UUID id) {
        return customerDao.findById(id).orElseThrow(() -> new BankApplicationException("Customer with id = " + id + " not found"));
    }
}
