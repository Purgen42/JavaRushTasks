package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 
Древний Рим
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {
        Map<String, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put("I", 1);
        romanNumerals.put("II", 2);
        romanNumerals.put("III", 3);
        romanNumerals.put("IV", 4);
        romanNumerals.put("V", 5);
        romanNumerals.put("VI", 6);
        romanNumerals.put("VII", 7);
        romanNumerals.put("VIII", 8);
        romanNumerals.put("IX", 9);
        romanNumerals.put("X", 10);
        romanNumerals.put("XX", 20);
        romanNumerals.put("XXX", 30);
        romanNumerals.put("XL", 40);
        romanNumerals.put("L", 50);
        romanNumerals.put("LX", 60);
        romanNumerals.put("LXX", 70);
        romanNumerals.put("LXXX", 80);
        romanNumerals.put("XC", 90);
        romanNumerals.put("C", 100);
        romanNumerals.put("CC", 200);
        romanNumerals.put("CCC", 300);
        romanNumerals.put("CD", 400);
        romanNumerals.put("D", 500);
        romanNumerals.put("DC", 600);
        romanNumerals.put("DCC", 700);
        romanNumerals.put("DCCC", 800);
        romanNumerals.put("CM", 900);
        romanNumerals.put("M", 1000);
        romanNumerals.put("MM", 2000);
        romanNumerals.put("MMM", 3000);

        Pattern tokenPattern = Pattern.compile("(DCCC|LXXX|VIII|LXX|XXX|CCC|DCC|III|MMM|VII|XX|LX|II|CC|MM|CD|CM|XC|IV|IX|VI|XL|DC|C|D|I|L|M|V|X)");
        Matcher tokenMatcher = tokenPattern.matcher(s.toUpperCase());
        int result = 0;
        while (tokenMatcher.find()) {
            result += romanNumerals.get(tokenMatcher.group());
        }

        return result;
    }
}
