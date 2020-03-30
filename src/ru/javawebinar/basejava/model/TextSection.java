package ru.javawebinar.basejava.model;

public class TextSection extends AbstractSection<String> {
    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void addInfo(String s) {
        text = s;
    }

    @Override
    public String toString() {
        return " '" + text + '\'';
    }
}
