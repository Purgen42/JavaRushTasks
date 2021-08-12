package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

final class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        Locale.setDefault(Locale.ENGLISH);
        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        int amount;
        Map<Integer, Integer> withdrawnDenomintaions = new TreeMap<>(Collections.reverseOrder());
        while (true) {
            String amountString = ConsoleHelper.readString();
            try {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                amount = Integer.parseInt(amountString);
                if (!manipulator.isAmountAvailable(amount)) {
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                    continue;
                }
                if (amount <= 0) {
                    ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                    continue;
                }
                withdrawnDenomintaions.putAll(manipulator.withdrawAmount(amount));
                break;
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        }
        for (Map.Entry<Integer, Integer> entry : withdrawnDenomintaions.entrySet()) {
            ConsoleHelper.writeMessage(entry.getKey() + "\t" + entry.getValue());
        }
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), amount, currencyCode));
    }
}
