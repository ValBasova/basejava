package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        updateElement(resume, searchElement(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        Object key = findElement(resume.getUuid());
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
        Object key = findElement(uuid);
        if (isElementExist(key)) {
            return key;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Object findElement(String uuid);

    protected abstract boolean isElementExist(Object o);

    protected abstract void updateElement(Resume resume, Object key);

    protected abstract void insertElement(Resume resume, Object key);

    protected abstract Resume getElement(Object o);

    protected abstract void removeElement(Object o);
}
