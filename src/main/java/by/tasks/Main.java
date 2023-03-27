package by.tasks;

import by.tasks.application.BankApplication;
import by.tasks.application.registry.ComponentRegistry;

public class Main {
    public static void main(String[] args) {
        ComponentRegistry.getComponent(BankApplication.class).run();
    }
}