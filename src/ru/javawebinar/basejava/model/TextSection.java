package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVertionUID = 1l;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    @Override
    public String toString() {
        return " '" + text + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
