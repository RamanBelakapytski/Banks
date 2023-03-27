package by.tasks.application.command.executor;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.customer.CustomerService;
import by.tasks.application.domain.customer.CustomerType;

import java.util.Arrays;
import java.util.List;

public class CustomerAddCommandExecutor implements CommandExecutor {

    private final CustomerService customerService;

    public CustomerAddCommandExecutor(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void execute(List<String> params) {
        if (params.size() != 2) {
            throw new BankApplicationException("Invalid parameters count");
        }
        var name = params.get(0);
        CustomerType customerType;
        try {
            customerType = CustomerType.valueOf(params.get(1));
        } catch (IllegalArgumentException e) {
            throw new BankApplicationException("Invalid value for customer type. Allowed values: " + Arrays.toString(CustomerType.values()));
        }

        System.out.println("Customer added: " + customerService.addCustomer(name, customerType));
    }
}
