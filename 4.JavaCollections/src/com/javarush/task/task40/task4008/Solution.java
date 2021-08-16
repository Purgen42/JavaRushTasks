package com.javarush.task.task40.task4008;

/*
Работа с Java 8 DateTime API
*/

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;

public class Solution {
    public static void main(String[] args) {
        printDate("9.10.2017 5:56:45");
        System.out.println();
        printDate("21.4.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        DateTimeFormatter formatter;
        LocalDate localDate;
        LocalTime localTime;

        if (date.matches("\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{1,2}:\\d{2}:\\d{2}")) {
            formatter = DateTimeFormatter.ofPattern("d.M.yyyy H:mm:ss");
            localDate = LocalDate.parse(date, formatter);
            localTime = LocalTime.parse(date, formatter);
            printOnlyDate(localDate);
            printOnlyTime(localTime);
        }
        if (date.matches("\\d{1,2}\\.\\d{1,2}\\.\\d{4}")) {
            formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            localDate = LocalDate.parse(date, formatter);
            printOnlyDate(localDate);
        }
        if (date.matches("\\d{1,2}:\\d{2}:\\d{2}")) {
            formatter = DateTimeFormatter.ofPattern("H:mm:ss");
            localTime = LocalTime.parse(date, formatter);
            printOnlyTime(localTime);
        }


    }

    private static void printOnlyDate(LocalDate date) {
        System.out.println("День: " + date.getDayOfMonth());
//        System.out.println("День недели: " + ((date.getDayOfWeek().getValue()) + 5) % 7 + 1));
        System.out.println("День недели: " + date.getDayOfWeek().getValue());
        System.out.println("День месяца: " + date.getDayOfMonth());
        System.out.println("День года: " + date.getDayOfYear());
        System.out.println("Неделя месяца: " + date.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfMonth()));
        System.out.println("Неделя года: " + date.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfYear()));
        System.out.println("Месяц: " + date.getMonth().getValue());
        System.out.println("Год: " + date.getYear());
//
    }

    private static void printOnlyTime(LocalTime time) {
        System.out.println("AM или PM: " + (time.get(ChronoField.AMPM_OF_DAY) == 0 ? "AM" : "PM"));
        System.out.println("Часы: " + time.get(ChronoField.HOUR_OF_AMPM));
        System.out.println("Часы дня: " + time.get(ChronoField.HOUR_OF_DAY));
        System.out.println("Минуты: " + time.get(ChronoField.MINUTE_OF_HOUR));
        System.out.println("Секунды: " + time.get(ChronoField.SECOND_OF_MINUTE));

    }

}
