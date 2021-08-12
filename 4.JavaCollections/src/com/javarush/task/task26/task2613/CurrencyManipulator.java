package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations;

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
        denominations = new HashMap<>();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        }
        else {
            denominations.put(denomination, count);
        }

    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> result = recursiveGreedySearch(expectedAmount, denominations);
        if (result == null) throw new NotEnoughMoneyException();
        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
            // Subtract returned values from denominations
            denominations.put(entry.getKey(), denominations.get(entry.getKey()) - entry.getValue());
            if (denominations.get(entry.getKey()) == 0) denominations.remove(entry.getKey());
        }
        return result;
    }

    public int getTotalAmount() {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            sum += entry.getKey() * entry.getValue();
        }
        return sum;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }

    public boolean hasMoney() {
        return getTotalAmount() > 0;
    }

    private Map<Integer, Integer> recursiveGreedySearch(int amount, Map<Integer, Integer> remainingDenominations) {

        if (amount == 0) return new HashMap<Integer, Integer>();
        Set<Integer> bankNotes = new TreeSet<>(Collections.reverseOrder());
        bankNotes.addAll(remainingDenominations.keySet());
        for (int value : bankNotes) {
            if (remainingDenominations.get(value) == 0) continue;
            if (value > amount) continue;
            Map<Integer, Integer> remainingDenominationsClone = new HashMap<>(remainingDenominations);
            remainingDenominationsClone.put(value, remainingDenominationsClone.get(value) - 1);
            Map<Integer, Integer> result = recursiveGreedySearch(amount - value, remainingDenominationsClone);
            if (result == null) continue;
            result.merge(value, 1, Integer::sum);
            return result;
        }
        return null;
    }
}
