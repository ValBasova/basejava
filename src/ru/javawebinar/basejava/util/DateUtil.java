package ru.javawebinar.basejava.util;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final YearMonth NOW = YearMonth.of(3000,01);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    public static String format(YearMonth date) {
        if (date == null) return "";
        return date.equals(NOW) ? "Сейчас" : date.format(DATE_FORMATTER);
    }

    public static YearMonth parse(String date) {
        if (date == null || date.trim().length() == 0 || "Сейчас".equals(date)) return NOW;
        return YearMonth.parse(date, DATE_FORMATTER);
    }
}
