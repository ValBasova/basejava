package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public List<Resume> getListResume() {
        return storage;
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
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isElementExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected void updateElement(Resume resume, Integer index) {
        storage.set(index, resume);
    }

    @Override
    public void insertElement(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    public Resume getElement(Integer index) {
        return storage.get(index);
    }

    @Override
    public void removeElement(Integer index) {
        storage.remove(index.intValue());
    }
}
