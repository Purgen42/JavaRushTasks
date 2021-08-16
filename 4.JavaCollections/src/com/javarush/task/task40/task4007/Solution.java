package com.javarush.task.task40.task4007;

/*
Работа с датами
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Solution {
    public static void main(String[] args) {
        printDate("21.04.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("15.08.2021");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        SimpleDateFormat sdf;
        try {
            if (date.matches("\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{1,2}:\\d{2}:\\d{2}")) {
                sdf = (new SimpleDateFormat("d.M.yyyy H:mm:ss"));
                calendar.setTime(sdf.parse(date));
                printOnlyDate(calendar);
                printOnlyTime(calendar);
            }
            if (date.matches("\\d{1,2}\\.\\d{1,2}\\.\\d{4}")) {
                sdf = (new SimpleDateFormat("d.M.yyyy"));
                calendar.setTime(sdf.parse(date));
                printOnlyDate(calendar);
            }
            if (date.matches("\\d{1,2}:\\d{2}:\\d{2}")) {
                sdf = (new SimpleDateFormat("H:mm:ss"));
                calendar.setTime(sdf.parse(date));
                printOnlyTime(calendar);
            }
          } catch (ParseException e) {
        }
    }

    private static void printOnlyDate(Calendar calendar) {
        System.out.println("День: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("День недели: " + ((calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1));
        System.out.println("День месяца: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("День года: " + calendar.get(Calendar.DAY_OF_YEAR));
        System.out.println("Неделя месяца: " + calendar.get(Calendar.WEEK_OF_MONTH));
        System.out.println("Неделя года: " + calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println("Месяц: " + (calendar.get(Calendar.MONTH) + 1));
        System.out.println("Год: " + calendar.get(Calendar.YEAR));

    }

    private static void printOnlyTime(Calendar calendar) {
        System.out.println("AM или PM: " + (calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM"));
        System.out.println("Часы: " + calendar.get(Calendar.HOUR));
        System.out.println("Часы дня: " + calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("Минуты: " + calendar.get(Calendar.MINUTE));
        System.out.println("Секунды: " + calendar.get(Calendar.SECOND));

    }

}
