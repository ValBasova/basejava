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
    protected Object findElement(String uuid) {
        for (int index = 0; index < storage.size(); index++) {
            if (storage.get(index).getUuid().equals(uuid)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected boolean isElementExist(Object o) {
        if ((int) o >= 0)
            return true;
        else
            return false;
    }

    @Override
    protected void updateElement(Resume resume, Object index) {
        storage.set((int) index, resume);
    }

    @Override
    public void insertElement(Resume resume, Object o) {
        storage.add(resume);
    }

    @Override
    public Resume getElement(Object o) {
        return storage.get((int) o);
    }

    @Override
    public void removeElement(Object o) {
        storage.remove((int) o);
    }
}
