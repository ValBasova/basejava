package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> nameComparator = Comparator.comparing(Resume::getFullName).
            thenComparing(Resume::getUuid);

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> storage = getListResume();
        storage.sort(nameComparator);
        return storage;
    }

    @Override
    public void update(Resume resume) {
        updateElement(resume, searchElement(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        Object key = getSearchKey(resume.getUuid());
        if (!isElementExist(key)) {
            insertElement(resume, key);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        return getElement(searchElement(uuid));
    }

    @Override
    public void delete(String uuid) {
        removeElement(searchElement(uuid));
    }

    protected Object searchElement(String uuid) {
        Object key = getSearchKey(uuid);
        if (isElementExist(key)) {
            return key;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract List getListResume();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isElementExist(Object key);

    protected abstract void updateElement(Resume resume, Object key);

    protected abstract void insertElement(Resume resume, Object key);

    protected abstract Resume getElement(Object key);

    protected abstract void removeElement(Object key);
}
