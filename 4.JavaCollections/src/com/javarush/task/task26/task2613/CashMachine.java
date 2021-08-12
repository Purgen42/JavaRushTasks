package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;

public class CashMachine {
    public static final String RESOURCE_PATH = CashMachine.class.getPackage().getName() + ".resources.";

    public static void main(String[] args) {
        Operation operation;
        boolean firstRun = true;
        do {
            try {
                if (firstRun) CommandExecutor.execute(Operation.LOGIN);
                firstRun = false;
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            } catch (InterruptOperationException e) {
//                ConsoleHelper.writeMessage("Операция прервана");
                break;
            }
        } while (operation != Operation.EXIT);
        ConsoleHelper.printExitMessage();
    }
}
