package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public interface Storage {

    public void update(Resume resume);

    public void save(Resume resume);

    public Resume get(String uuid);

    public Resume[] getAll();

    public void delete(String uuid);

    public void clear();

    public int size();
}
