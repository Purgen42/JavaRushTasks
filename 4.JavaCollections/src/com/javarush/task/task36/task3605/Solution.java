package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

/* 
Использование TreeSet
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
//        String fileName = "C:\\t\\1.txt";
        TreeSet<Character> treeSet = new TreeSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int charInt = reader.read();
            while (charInt != -1) {
                char currentChar = (char) charInt;
                if (Character.isLetter(currentChar)) treeSet.add(Character.toLowerCase(currentChar));
                charInt = reader.read();
            }
        }
        StringBuilder builder = new StringBuilder();
        treeSet.stream().limit(5).forEach(builder::append);
        System.out.println(builder);
    }
}
