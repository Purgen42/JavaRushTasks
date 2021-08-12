package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");

    public static Operation askOperation() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("choose.operation"));
        ConsoleHelper.writeMessage("1 " + res.getString("operation.INFO"));
        ConsoleHelper.writeMessage("2 " + res.getString("operation.DEPOSIT"));
        ConsoleHelper.writeMessage("3 " + res.getString("operation.WITHDRAW"));
        ConsoleHelper.writeMessage("4 " + res.getString("operation.EXIT"));
        while (true) {
            try {
                int operationIndex = Integer.parseInt(readString());
                return Operation.getAllowableOperationByOrdinal(operationIndex);
            }
            catch (IllegalArgumentException e) {

            }
        }
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("choose.currency.code"));
        String currenceCode = readString();
        if (!currenceCode.matches("[a-zA-Z]{3}")) {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));

            throw new IllegalArgumentException();
        }
//        while (!currenceCode.matches("[a-zA-Z]{3}")) {
//            writeMessage("Неверный формат кода, введите три символа");
//            currenceCode = readString();
//        }
        return currenceCode.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        ConsoleHelper.writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
        String input = readString();
        Pattern pattern = Pattern.compile("^(\\d+) (\\d+)$");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));

            throw new IllegalArgumentException();
        }
//        while (!matcher.find()) {
//            writeMessage("Неверный формат номинала и количества, введите два числа");
//            input = readString();
//            matcher = pattern.matcher(input);
//        }

        return new String[] {matcher.group(1), matcher.group(2)};
    }

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String result = "";
        try {
            result = bis.readLine();
        } catch (IOException e) {
        }
        if (result.toUpperCase().equals("EXIT")) {
            printExitMessage();
            throw new InterruptOperationException();
        }

        return result;
    }

    public static void printExitMessage () {
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }
}
