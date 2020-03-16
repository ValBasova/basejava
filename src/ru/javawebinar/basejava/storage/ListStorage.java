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
    protected int findElement(String uuid) {
        for (int index = 0; index < storage.size(); index++) {
            if (storage.get(index).getUuid().equals(uuid)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected void updateElement(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    public void insertElement(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    public Resume getElement(int index) {
        return storage.get(index);
    }

    @Override
    public void removeElement(int index) {
        storage.remove(index);
    }
}
