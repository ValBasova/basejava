package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public interface Storage {

    public void update(Resume resume);

    public void save(Resume resume);

    public Resume get(String uuid);

    public List<Resume> getAllSorted();

    public void delete(String uuid);

    public void clear();

    public int size();
}
