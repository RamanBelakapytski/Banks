package by.tasks.application.command.executor;

import java.util.List;

public class ExitCommandExecutor implements CommandExecutor{
    @Override
    public void execute(List<String> params) {
        System.exit(0);
    }
}
