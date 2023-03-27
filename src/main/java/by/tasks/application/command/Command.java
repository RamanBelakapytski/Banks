package by.tasks.application.command;

import by.tasks.application.command.executor.*;

import java.util.List;

public record Command(
        CommandType commandType, List<String> params
) {
    public enum CommandType {
        BANKS_ADD("Add a new bank. Parameters: BANK_NAME LEGAL_FEE NATURAL_FEE", BankAddCommandExecutor.class),
        BANKS_UPDATE_FEE("Update fee for a bank. Parameters: BANK_ID LEGAL_FEE NATURAL_FEE", BankUpdateCommandExecutor.class),
        BANKS_GET_ALL("Get list of all banks", BanksGetAllCommandExecutor.class),
        BANKS_ADD_CUSTOMER("Add customer to bank. Parameters: BANK_ID CUSTOMER_ID", BankAddCustomerCommandExecutor.class),
        CUSTOMERS_ADD("Add new customer. Parameters: NAME CUSTOMER_TYPE", CustomerAddCommandExecutor.class),
        CUSTOMERS_ADD_ACCOUNT("Open new account for customer. Parameters: BANK_ID CUSTOMER_ID CURRENCY BALANCE", CustomerAddAccountCommandExecutor.class),
        CUSTOMERS_GET_ALL("Get list of all customers", CustomersGetAllCommandExecutor.class),
        CUSTOMERS_GET_ACCOUNTS("Get list of customer accounts. Parameters: CUSTOMER_ID", CustomerGetAccountsCommandExecutor.class),
        EXIT("Exit app", ExitCommandExecutor.class),
        HELP("Get info about all commands", HelpCommandExecutor.class);

        CommandType(String description, Class<? extends CommandExecutor> executor) {
            this.description = description;
            this.executor = executor;
        }

        private final String description;
        private final Class<? extends CommandExecutor> executor;

        public String getDescription() {
            return description;
        }

        public Class<? extends CommandExecutor> getExecutor() {
            return executor;
        }
    }


}
