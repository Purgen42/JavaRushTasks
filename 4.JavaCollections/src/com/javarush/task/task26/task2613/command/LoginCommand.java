package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command {
//    private final String CARD_NUMBER = "123456789012";
//    private final String PIN = "1234";
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        while(true) {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            String userCardNumber = ConsoleHelper.readString();
            String userPin = ConsoleHelper.readString();
            if (!userCardNumber.matches("\\d{12}") || !userPin.matches("\\d{4}")) {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }
            else if (validCreditCards.containsKey(userCardNumber) && userPin.equals(validCreditCards.getString(userCardNumber))) {
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), userCardNumber));
                break;
            }
            else ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), userCardNumber));
            ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
        }
    }
}
