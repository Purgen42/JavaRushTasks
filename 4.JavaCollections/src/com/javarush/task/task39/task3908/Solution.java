package com.javarush.task.task39.task3908;

/* 
Возможен ли палиндром?
*/

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
//        System.out.println(isPalindromePermutation("Tenet1"));
    }

    public static boolean isPalindromePermutation(String s) {
        if (s == null) return false;
        if (s.isEmpty()) return false;
        byte[] bytes = s.toLowerCase().getBytes();
        Map<Byte, Integer> charCount = new HashMap<>();
        for (byte aByte : bytes) {
            if (charCount.containsKey(aByte)) {
                charCount.put(aByte, charCount.get(aByte) + 1);
            } else {
                charCount.put(aByte, 1);
            }
        }
        int oddCount = 0;
        for (Map.Entry<Byte, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() % 2 == 1) oddCount++;
            if (oddCount > 1) return false;
        }
        return true;
    }
}
