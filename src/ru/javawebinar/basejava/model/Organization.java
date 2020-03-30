package ru.javawebinar.basejava.model;

import java.time.YearMonth;

public class Organization {
    private String organizationName;
    private YearMonth timeStart;
    private YearMonth timeEnd;
    private String contеnt;

    public Organization(String organizationName, String timeStart, String timeEnd, String contеnt) {
        this.organizationName = organizationName;
        this.timeStart = YearMonth.parse(timeStart);
        this.timeEnd = YearMonth.parse(timeEnd);
        this.contеnt = contеnt;
    }
}
