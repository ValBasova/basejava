package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listResume = new ArrayList<Resume>(storage.values());
        listResume.sort(nameComparator);
        return listResume;

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
    protected Object getSearchKey(String uuid) {
        return uuid;
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
    protected Resume getElement(Object key) {
        return storage.get(key);
    }

    @Override
    protected void removeElement(Object key) {
        storage.remove(key);
    }
}
