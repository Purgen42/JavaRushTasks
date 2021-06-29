package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpeedTest {
    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date startTime = new Date();
        for (String s : strings) {
            ids.add(shortener.getId(s));
        }
        Date endTime = new Date();
        return endTime.getTime() - startTime.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date startTime = new Date();
        for (Long id : ids) {
            strings.add(shortener.getString(id));
        }
        Date endTime = new Date();
        return endTime.getTime() - startTime.getTime();
    }

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = Stream
                .generate(Helper::generateRandomString)
                .limit(10000)
                .collect(Collectors.toCollection(HashSet<String>::new));
        Set<Long> ids = new HashSet<>();
        Set<String> newStrings = new HashSet<>();
        long getIdTime1 = getTimeToGetIds(shortener1, origStrings, ids);
        ids.clear();
        long getIdTime2 = getTimeToGetIds(shortener2, origStrings, ids);
        Assert.assertTrue(getIdTime1 > getIdTime2);
        long geStringTime1 = getTimeToGetStrings(shortener1, ids, newStrings);
        newStrings.clear();
        long getStringTime2 = getTimeToGetStrings(shortener2, ids, newStrings);
        Assert.assertEquals(geStringTime1, getStringTime2, 30);
    }
}
