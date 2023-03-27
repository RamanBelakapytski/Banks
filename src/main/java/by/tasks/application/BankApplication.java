package by.tasks.application;

import java.util.Scanner;

public class BankApplication {

    private CommandParser commandParser = new CommandParser();
    private CommandExecutor commandExecutor = new CommandExecutor();

    public void run() {
        final var scanner = new Scanner(System.in);
        var input = "";
        do {
            input = scanner.nextLine();

            try {
                final var command = commandParser.parse(input);
                commandExecutor.execute(command);
            } catch (BankApplicationException e) {
                System.out.println(e.getMessage());
            }

        } while (!"EXIT".equals(input));
    }
}
