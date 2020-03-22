package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> nameComparator = (o1, o2) -> {
        int i = o1.getFullName().compareTo(o2.getFullName());
        if (i == 0) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
        return i;
    };

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

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isElementExist(Object key);

    protected abstract void updateElement(Resume resume, Object key);

    protected abstract void insertElement(Resume resume, Object key);

    protected abstract Resume getElement(Object key);

    protected abstract void removeElement(Object key);
}
