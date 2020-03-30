package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public List<Resume> getListResume() {
        return new ArrayList<>(storage.values());
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
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isElementExist(String  key) {
        return storage.containsKey(key);
    }

    @Override
    protected void updateElement(Resume resume, String key) {
        storage.put(key, resume);
    }

    @Override
    protected void insertElement(Resume resume, String key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(String key) {
        return storage.get(key);
    }

    @Override
    protected void removeElement(String key) {
        storage.remove(key);
    }
}
