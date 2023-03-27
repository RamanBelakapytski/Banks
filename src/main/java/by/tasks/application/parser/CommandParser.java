package by.tasks.application.parser;

import by.tasks.application.BankApplicationException;
import by.tasks.application.command.Command;

import java.util.Arrays;

import static java.util.function.Predicate.not;

public class CommandParser {
    private final static String DELIMETER = " ";

    public Command parse(String input) {
        if (input == null || input.isBlank()) {
            throw new BankApplicationException("Input should not be empty");
        }

        final var tokens = input.split(DELIMETER);

        final Command.CommandType command;
        try {
            command = Command.CommandType.valueOf(tokens[0]);
        } catch (IllegalArgumentException e) {
            throw new BankApplicationException("Invalid command " + tokens[0] + ", allowed commands " + Arrays.toString(Command.CommandType.values()));
        }

        return new Command(command, Arrays.stream(tokens).skip(1).filter(not(String::isBlank)).toList());
    }
}
