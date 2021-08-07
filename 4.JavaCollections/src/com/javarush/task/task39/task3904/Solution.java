package com.javarush.task.task39.task3904;

import java.util.Arrays;

/* 
Лестница
*/

public class Solution {
    private static int n = 70;

    public static void main(String[] args) {
        System.out.println("The number of possible ascents for " + n + " steps is: " + numberOfPossibleAscents(n));
    }

    public static long numberOfPossibleAscents(int n) {
        if (n < 0) return 0;
        long[] numberOfPossibles = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            if (i == 0) numberOfPossibles[i] = 1;
            else if (i == 1) numberOfPossibles[i] = 1;
            else if (i == 2) numberOfPossibles[i] = 2;
            else numberOfPossibles[i] = numberOfPossibles[i - 1] + numberOfPossibles[i - 2] + numberOfPossibles[i - 3];
        }
        return numberOfPossibles[n];
    }

}

