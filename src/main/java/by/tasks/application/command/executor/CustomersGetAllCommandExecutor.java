package by.tasks.application.command.executor;

import by.tasks.application.domain.customer.CustomerService;

import java.util.List;

public class CustomersGetAllCommandExecutor implements CommandExecutor {

    private final CustomerService customerService;

    public CustomersGetAllCommandExecutor(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void execute(List<String> params) {
        System.out.println("All customers: " + customerService.findAll());
    }
}
