package by.tasks.application.command.executor;

import by.tasks.application.command.Command;

import java.util.Arrays;
import java.util.List;

public class HelpCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> params) {
        Arrays.stream(Command.CommandType.values())
                .map(command -> command.name() + " " + command.getDescription())
                .forEach(System.out::println);
    }
}
