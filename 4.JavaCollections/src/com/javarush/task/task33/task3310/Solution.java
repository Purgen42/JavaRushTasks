package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;
import com.javarush.task.task33.task3310.tests.FunctionalTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {
    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        return strings
                .stream()
                .map(shortener::getId)
                .collect(Collectors.toCollection(HashSet<Long>::new));
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        return keys
                .stream()
                .map(shortener::getString)
                .collect(Collectors.toCollection(HashSet<String>::new));
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());
        HashSet<String> testStrings = Stream
                .generate(Helper::generateRandomString)
                .limit(elementsNumber)
                .collect(Collectors.toCollection(HashSet<String>::new));
        Shortener shortener = new Shortener(strategy);

        Date getIdsStart = new Date();
        Set<Long> testIds = getIds(shortener, testStrings);
        Date getIdsEnd = new Date();
        Helper.printMessage(String.valueOf(getIdsEnd.getTime() - getIdsStart.getTime()));

        Date getStringsStart = new Date();
        Set<String> anotherStrings = getStrings(shortener, testIds);
        Date getStringsEnd = new Date();
        Helper.printMessage(String.valueOf(getStringsEnd.getTime() - getStringsStart.getTime()));

        Helper.printMessage(anotherStrings.equals(testStrings) ? "Тест пройден." : "Тест не пройден.");
    }

    public static void main(String[] args) {
//        testStrategy(new HashMapStorageStrategy(), 10000);
//        testStrategy(new OurHashMapStorageStrategy(), 10000);
//        testStrategy(new FileStorageStrategy(), 100);
//        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
//        testStrategy(new HashBiMapStorageStrategy(), 10000);
//        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);

    }
}
