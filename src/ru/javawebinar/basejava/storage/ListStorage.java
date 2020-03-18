package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer findElement(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isElementExist(Object index) {
        return (int) index >= 0;
    }

    @Override
    protected void updateElement(Resume resume, Object index) {
        storage.set((int) index, resume);
    }

    @Override
    public void insertElement(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    public Resume getElement(Object index) {
        return storage.get((int) index);
    }

    @Override
    public void removeElement(Object index) {
        storage.remove((int) index);
    }
}
