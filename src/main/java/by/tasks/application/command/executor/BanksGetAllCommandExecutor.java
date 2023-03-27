package by.tasks.application.command.executor;

import by.tasks.application.domain.bank.BankDao;

import java.util.List;

public class BanksGetAllCommandExecutor implements CommandExecutor{

    private final BankDao bankDao;

    public BanksGetAllCommandExecutor(BankDao bankDao) {
        this.bankDao = bankDao;
    }
    @Override
    public void execute(List<String> params) {
        System.out.println("All banks: " + bankDao.findAll());
    }
}
