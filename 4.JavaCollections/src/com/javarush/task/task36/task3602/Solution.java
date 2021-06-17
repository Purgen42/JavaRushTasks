package com.javarush.task.task36.task3602;

import java.util.Collections;

/* 
Найти класс по описанию Ӏ Java Collections: 6 уровень, 6 лекция
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class collections = Collections.class;
        Class[] classes = collections.getDeclaredClasses();
        for (Class c : classes) {
            if (c.getSimpleName().equals("EmptyList")) return c;
        }
        return null;
    }
}
