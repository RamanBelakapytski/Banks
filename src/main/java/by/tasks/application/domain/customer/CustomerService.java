package by.tasks.application.domain.customer;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.account.AccountService;
import by.tasks.application.domain.bank.BankService;

import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao;
    private final BankService bankService;
    private final AccountService accountService;

    public CustomerService(CustomerDao customerDao, BankService bankService, AccountService accountService) {
        this.customerDao = customerDao;
        this.bankService = bankService;
        this.accountService = accountService;
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
