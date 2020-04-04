package ru.javawebinar.basejava.model;

import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String name;
    private URL url;
    private List<Position> positions;

    public Organization(String name, URL url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url;
        positions = new ArrayList<>();
    }

    private static class Position {
        private YearMonth timeStart;
        private YearMonth timeEnd;
        private String title;
        private String description;

        public Position(String timeStart, String timeEnd, String title, String description) {
            Objects.requireNonNull(timeStart, "timeStart must not be null");
            Objects.requireNonNull(timeEnd, "timeEnd must not be null");
            Objects.requireNonNull(description, "description must not be null");
            if (title == null) {
                this.title = "Student";
            } else {
                this.title = title;
            }
            this.description = description;
            this.timeStart = YearMonth.parse(timeStart);
            this.timeEnd = YearMonth.parse(timeEnd);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "timeStart=" + timeStart +
                    ", timeEnd=" + timeEnd +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public void addPosition(String timeStart, String timeEnd, String title, String description) {
        positions.add(new Position(timeStart, timeEnd, title, description));
    }

    @Override
    public String toString() {
        return " {" +
                " '" + name + '\'' +
                ", " + url +
                ", " + positions +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return name.equals(that.name) &&
                Objects.equals(url, that.url) &&
                positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, positions);
    }
}
