package by.tasks.application.registry;

import by.tasks.application.BankApplication;
import by.tasks.application.command.executor.*;
import by.tasks.application.database.Database;
import by.tasks.application.domain.account.AccountDao;
import by.tasks.application.domain.account.AccountService;
import by.tasks.application.domain.bank.BankDao;
import by.tasks.application.domain.bank.BankService;
import by.tasks.application.domain.customer.CustomerDao;
import by.tasks.application.domain.customer.CustomerService;
import by.tasks.application.domain.transaction.FeeCalculator;
import by.tasks.application.domain.transaction.TransactionDao;
import by.tasks.application.domain.transaction.TransactionService;
import by.tasks.application.parser.CommandParser;
import by.tasks.application.processor.CommandProcessor;

import java.util.HashMap;
import java.util.Map;

public class ComponentRegistry {
    private static final Map<Class<?>, Object> CONTEXT = new HashMap<>();

    static {
        CONTEXT.put(Database.class, new Database());
        CONTEXT.put(CommandParser.class, new CommandParser());

        CONTEXT.put(BankDao.class, new BankDao(getComponent(Database.class)));
        CONTEXT.put(CustomerDao.class, new CustomerDao(getComponent(Database.class)));
        CONTEXT.put(AccountDao.class, new AccountDao(getComponent(Database.class)));
        CONTEXT.put(TransactionDao.class, new TransactionDao(getComponent(Database.class)));

        CONTEXT.put(BankService.class, new BankService(getComponent(BankDao.class), getComponent(CustomerDao.class), getComponent(AccountDao.class)));
        CONTEXT.put(AccountService.class, new AccountService(getComponent(AccountDao.class), getComponent(BankDao.class), getComponent(CustomerDao.class)));
        CONTEXT.put(CustomerService.class, new CustomerService(getComponent(CustomerDao.class), getComponent(BankDao.class), getComponent(AccountDao.class)));

        CONTEXT.put(ExitCommandExecutor.class, new ExitCommandExecutor());
        CONTEXT.put(HelpCommandExecutor.class, new HelpCommandExecutor());
        CONTEXT.put(BankAddCommandExecutor.class, new BankAddCommandExecutor(getComponent(BankService.class)));
        CONTEXT.put(BanksGetAllCommandExecutor.class, new BanksGetAllCommandExecutor(getComponent(BankService.class)));
        CONTEXT.put(CustomersGetAllCommandExecutor.class, new CustomersGetAllCommandExecutor(getComponent(CustomerService.class)));
        CONTEXT.put(CustomerAddCommandExecutor.class, new CustomerAddCommandExecutor(getComponent(CustomerService.class)));
        CONTEXT.put(BankUpdateCommandExecutor.class, new BankUpdateCommandExecutor(getComponent(BankService.class)));
        CONTEXT.put(BankAddCustomerCommandExecutor.class, new BankAddCustomerCommandExecutor(getComponent(BankService.class)));
        CONTEXT.put(CustomerGetAccountsCommandExecutor.class, new CustomerGetAccountsCommandExecutor(getComponent(AccountService.class)));
        CONTEXT.put(FeeCalculator.class, new FeeCalculator(getComponent(BankDao.class), getComponent(CustomerDao.class)));
        CONTEXT.put(TransactionService.class, new TransactionService(getComponent(Database.class), getComponent(AccountDao.class), getComponent(FeeCalculator.class), getComponent(TransactionDao.class)));
        CONTEXT.put(CustomerAddAccountCommandExecutor.class, new CustomerAddAccountCommandExecutor(getComponent(AccountService.class)));

        CONTEXT.put(CommandProcessor.class, new CommandProcessor());
        CONTEXT.put(BankApplication.class, new BankApplication(getComponent(CommandParser.class), getComponent(CommandProcessor.class)));
    }

    public static <T> T getComponent(Class<T> clazz) {
        final var component = CONTEXT.get(clazz);
        if (component == null) {
            throw new RuntimeException("Failed to get " + clazz + " from context");
        }
        return (T) CONTEXT.get(clazz);
    }
}
