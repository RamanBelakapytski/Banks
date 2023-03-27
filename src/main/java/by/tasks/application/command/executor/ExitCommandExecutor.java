package by.tasks.application.command.executor;

import by.tasks.application.command.Command;

import java.util.List;

public class ExitCommandExecutor implements CommandExecutor{
    @Override
    public void execute(List<String> params) {
        System.exit(0);
    }
}
