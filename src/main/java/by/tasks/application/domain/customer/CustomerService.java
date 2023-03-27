package by.tasks.application.domain.customer;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.account.AccountDao;
import by.tasks.application.domain.bank.BankDao;

import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao;
    private final BankDao bankDao;
    private final AccountDao accountDao;

    public CustomerService(CustomerDao customerDao, BankDao bankDao, AccountDao accountDao) {
        this.customerDao = customerDao;
        this.bankDao = bankDao;
        this.accountDao = accountDao;
    }

    public Customer addCustomer(String name, CustomerType customerType) {
        if (customerDao.existsByName(name)) {
            throw new BankApplicationException("Customer with name `" + name + "` already exists");
        }

        return customerDao.save(new Customer(name, customerType));
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }
}
