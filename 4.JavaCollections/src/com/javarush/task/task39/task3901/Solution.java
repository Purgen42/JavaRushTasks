package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/* 
Уникальные подстроки
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String s = bufferedReader.readLine();

        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(s));
    }

    public static int lengthOfLongestUniqueSubstring(String s) {
        if (s == null) return 0;
        StringBuilder subString = new StringBuilder();
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            String currentChar = s.substring(i, i + 1);
            while (subString.indexOf(currentChar) != -1 && subString.length() > 0) {
//                System.out.println(subString + " " + subString.indexOf(currentChar));
                subString.deleteCharAt(0);
            }
            subString.append(currentChar);
            int currentLength = subString.length();
            if (currentLength > maxLength) maxLength = currentLength;
        }
        return maxLength;
    }
}
