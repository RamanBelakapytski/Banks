package by.tasks.application.command.executor;

import java.util.List;

public interface CommandExecutor {
    void execute(List<String> params);
}
