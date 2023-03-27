package by.tasks.application.command.executor;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.bank.BankService;

import java.util.List;
import java.util.UUID;

public class BankAddCustomerCommandExecutor implements CommandExecutor {
    private final BankService bankService;

    public BankAddCustomerCommandExecutor(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public void execute(List<String> params) {
        if (params.size() != 2) {
            throw new BankApplicationException("Invalid parameters count");
        }
        UUID bankId;
        UUID customerId;

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

        System.out.println("Customer added to bank with new account " + bankService.addCustomer(bankId, customerId));
    }
}
