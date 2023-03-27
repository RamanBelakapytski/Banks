package by.tasks.application.domain.transaction;

import by.tasks.application.domain.account.Account;
import by.tasks.application.domain.bank.BankDao;
import by.tasks.application.domain.customer.CustomerDao;

import java.math.BigDecimal;

public class FeeCalculator {
    private final BankDao bankDao;
    private final CustomerDao customerDao;

    public FeeCalculator(BankDao bankDao, CustomerDao customerDao) {
        this.bankDao = bankDao;
        this.customerDao = customerDao;
    }

    public BigDecimal calculateFee(Account from, Account to) {
        if (from.getBankId().equals(to.getBankId())) {
            return BigDecimal.ZERO;
        }

        var bank = bankDao.findById(from.getBankId()).orElseThrow();
        var customer = customerDao.findById(from.getCustomerId()).orElseThrow();

        return bank.getFee().get(customer.getCustomerType());
    }
}
