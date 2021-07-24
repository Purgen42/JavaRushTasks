package com.javarush.task.task38.task3812;

/* 
Обработка аннотаций
*/

public class Solution {
    public static void main(String[] args) {
        printFullyQualifiedNames(Solution.class);
        printFullyQualifiedNames(SomeTest.class);

        printValues(Solution.class);
        printValues(SomeTest.class);
    }

    public static boolean printFullyQualifiedNames(Class c) {
        if (c.isAnnotationPresent(PrepareMyTest.class)) {
            String[] fullyQualifiedNames = ((PrepareMyTest) c.getAnnotation(PrepareMyTest.class)).fullyQualifiedNames();
            for (String s : fullyQualifiedNames) {
                System.out.println(s);
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean printValues(Class c) {
        if (c.isAnnotationPresent(PrepareMyTest.class)) {
            Class<?>[] value = ((PrepareMyTest) c.getAnnotation(PrepareMyTest.class)).value();
            for (Class<?> clazz : value) {
                System.out.println(clazz.getSimpleName());
            }
            return true;
        }
        else {
            return false;
        }
    }
}
