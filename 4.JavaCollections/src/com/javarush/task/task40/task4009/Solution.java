package com.javarush.task.task40.task4009;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/* 
Buon Compleanno!
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getWeekdayOfBirthday("30.05.1984", "2015"));
        System.out.println(getWeekdayOfBirthday("1.12.2015", "2016"));
    }

    public static String getWeekdayOfBirthday(String birthday, String year) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate localBirthday = LocalDate.parse(birthday, formatter);
        Year localYear = Year.parse(year);
        LocalDate yearsBirthday = localYear.atMonthDay(MonthDay.of(localBirthday.getMonth(), localBirthday.getDayOfMonth()));
        return yearsBirthday.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
    }
}
