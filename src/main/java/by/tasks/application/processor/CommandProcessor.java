package by.tasks.application.processor;

import by.tasks.application.BankApplicationException;
import by.tasks.application.command.Command;
import by.tasks.application.command.executor.CommandExecutor;
import by.tasks.application.registry.ComponentRegistry;

import java.util.Arrays;
import java.util.Map;

import static by.tasks.application.command.Command.CommandType.values;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class CommandProcessor {
    private final Map<Command.CommandType, CommandExecutor> commandExecutors =
            Arrays.stream(values())
                    .collect(
                            toMap(
                                    identity(),
                                    commandType -> ComponentRegistry.getComponent(commandType.getExecutor())
                            )
                    );
    public void process(Command command) {
        final var executor = commandExecutors.get(command.commandType());

        if (executor == null) {
            throw new BankApplicationException("Could not find executor for " + command.commandType() + " command");
        }

        executor.execute(command.params());
    }
}
