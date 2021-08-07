package com.javarush.task.task39.task3909;

/* 
Одно изменение
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        System.out.println(isOneEditAway("", ""));
    }

    public static boolean isOneEditAway(String first, String second) {
        if (first == null || second == null) return false;
        if (first.equals(second)) return true;
        if (first.length() == second.length()) return compareEqualLength(first.getBytes(), second.getBytes());
        if (first.length() - second.length() == 1) return compareDifferentLength(second.getBytes(), first.getBytes());
        if (second.length() - first.length() == 1) return compareDifferentLength(first.getBytes(), second.getBytes());
        return false;
    }

    private static boolean compareEqualLength (byte[] a, byte[] b) {
        for (int i = 0; i < a.length; i++) {
            byte[] aCopy = Arrays.copyOf(a, a.length);
            aCopy[i] = b[i];
            if (Arrays.equals(aCopy, b)) return true;
        }
        return false;
    }

    private static boolean compareDifferentLength(byte[] small, byte[] big) {
        for (int i = 0; i < big.length; i++) {
            byte[] bigRemoved = removeByte(big, i);
            if (Arrays.equals(bigRemoved, small)) return true;
        }
        return false;
    }
    
    private static byte[] removeByte(byte[] array, int index) {
        byte[] result = new byte[array.length - 1];
        int resultIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (i == index) continue;
            result[resultIndex++] = array[i];
        }
        return result;
    }
}
