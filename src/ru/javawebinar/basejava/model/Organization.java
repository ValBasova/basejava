package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVertionUID = 1l;

    private String name;
    private URL url;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(String name, URL url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url;
        positions = new ArrayList<>();
    }

    public Organization(String name) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        positions = new ArrayList<>();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth timeStart;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth timeEnd;
        private String title;
        private String description;

        public Position(){
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(timeStart, position.timeStart) &&
                    Objects.equals(timeEnd, position.timeEnd) &&
                    Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(timeStart, timeEnd, title, description);
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
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, positions);
    }
}
