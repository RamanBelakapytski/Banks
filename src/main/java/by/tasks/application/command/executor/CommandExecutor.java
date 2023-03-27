package by.tasks.application.command.executor;

import by.tasks.application.command.Command;

import java.util.List;

public interface CommandExecutor {
    void execute(List<String> params);
}
