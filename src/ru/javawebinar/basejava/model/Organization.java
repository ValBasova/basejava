package ru.javawebinar.basejava.model;

import java.net.URL;
import java.time.YearMonth;
import java.util.Objects;

public class Organization {
    private String name;
    private URL url;
    private YearMonth timeStart;
    private YearMonth timeEnd;
    private String contеnt;

    public Organization(String name, URL url, String timeStart, String timeEnd, String content) {
        this.name = name;
        this.url = url;
        this.timeStart = YearMonth.parse(timeStart);
        this.timeEnd = YearMonth.parse(timeEnd);
        this.contеnt = content;
    }

    @Override
    public String toString() {
        return " {" + name + '\'' +
                ", " + timeStart +
                ", " + timeEnd +
                ", '" + contеnt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url) &&
                Objects.equals(timeStart, that.timeStart) &&
                Objects.equals(timeEnd, that.timeEnd) &&
                Objects.equals(contеnt, that.contеnt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, timeStart, timeEnd, contеnt);
    }
}
