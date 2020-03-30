package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection<ArrayList<String>> {
    private List<String> textList;

    public ListSection() {
        this.textList = new ArrayList<>();
    }

    public List<String> getTextList() {
        return textList;
    }

    @Override
    public void addInfo(ArrayList list) {
        textList = list;
    }

    @Override
    public String toString() {
        return " " + textList;
    }
}
