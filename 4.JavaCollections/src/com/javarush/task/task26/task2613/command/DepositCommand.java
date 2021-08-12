package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

final class DepositCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        Locale.setDefault(Locale.ENGLISH);
        String currencyCode = null;
        while (true) {
            try {
                currencyCode = ConsoleHelper.askCurrencyCode();
                break;
            } catch (IllegalArgumentException e) {
//                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
        }
        CurrencyManipulator manipulator = null;
        manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        String[] amount;
        while (true) {
            try {
                amount = ConsoleHelper.getValidTwoDigits(currencyCode);
                break;
            } catch (IllegalArgumentException e) {
//                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
        }
        int denomination = Integer.parseInt(amount[0]);
        int count = Integer.parseInt(amount[1]);
        manipulator.addAmount(denomination, count);
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), denomination * count, currencyCode));
    }
}
