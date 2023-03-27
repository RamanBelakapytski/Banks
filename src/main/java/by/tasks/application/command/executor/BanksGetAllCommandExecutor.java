package by.tasks.application.command.executor;

import by.tasks.application.domain.bank.BankService;

import java.util.List;

public class BanksGetAllCommandExecutor implements CommandExecutor {

    private final BankService bankService;

    public BanksGetAllCommandExecutor(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public void execute(List<String> params) {
        System.out.println("All banks: " + bankService.findAll());
    }
}
