package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVertionUID = 1l;

    private List<String> textList;

    public final static ListSection EMPTY = new ListSection();

    public ListSection() {
        this.textList = new ArrayList<>();
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        Objects.requireNonNull(textList, "textList must not be null");
        this.textList = textList;
    }

    @Override
    public String toString() {
        return " " + textList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(textList, that.textList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textList);
    }
}
