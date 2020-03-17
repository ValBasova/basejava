package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
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
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return -1;
    }

    @Override
    protected boolean isElementExist(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected void updateElement(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected void insertElement(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object o) {
        return storage.get(o);
    }

    @Override
    protected void removeElement(Object o) {
        storage.remove(o);
    }
}
