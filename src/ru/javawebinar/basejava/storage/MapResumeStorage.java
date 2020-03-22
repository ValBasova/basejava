package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

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
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isElementExist(Object resume) {
        return storage.containsValue(resume);
    }

    @Override
    protected void updateElement(Resume r, Object resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void insertElement(Resume r, Object resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getElement(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void removeElement(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }
}
