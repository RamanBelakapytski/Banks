package by.tasks.application.command;

import by.tasks.application.command.executor.*;

import java.util.List;

public record Command(
        CommandType commandType, List<String> params
) {
    public enum CommandType {
        BANKS_ADD("Add a new bank. Parameters: BANK_NAME LEGAL_FEE NATURAL_FEE", BankAddCommandExecutor.class),
        BANKS_GET_ALL("Get list of all banks", BanksGetAllCommandExecutor.class),
        CUSTOMERS_GET_ALL("Get list of all customers", CustomersGetAllCommandExecutor.class),
        EXIT("Exit app", ExitCommandExecutor.class),
        HELP("Get info about all commands", HelpCommandExecutor.class);

        private CommandType(String description, Class<? extends CommandExecutor> executor) {
            this.description = description;
            this.executor = executor;
        }

        private String description;
        private Class<? extends CommandExecutor> executor;

        public String getDescription() {
            return description;
        }

        public Class<? extends CommandExecutor> getExecutor() {
            return executor;
        }
    }


}
