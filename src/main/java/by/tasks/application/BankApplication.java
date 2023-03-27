package by.tasks.application;

import by.tasks.application.parser.CommandParser;
import by.tasks.application.processor.CommandProcessor;

import java.util.Scanner;

public class BankApplication {

    private final CommandParser commandParser;
    private final CommandProcessor commandProcessor;

    public BankApplication(CommandParser commandParser, CommandProcessor commandProcessor) {
        this.commandParser = commandParser;
        this.commandProcessor = commandProcessor;
    }

    public void run() {
        final var scanner = new Scanner(System.in);
        var input = "";

        while (true) {
            System.out.println("Provide new command (type HELP to see all commands):");
            input = scanner.nextLine();

            try {
                commandProcessor.process(commandParser.parse(input));
            } catch (BankApplicationException e) {
                System.out.println("Command not processed: " + e.getMessage());
            }

        }
    }
}
