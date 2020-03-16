package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        updateElement(resume, seachElement(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        int index = findElement(resume.getUuid());
        if (index < 0) {
            insertElement(resume, index);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        return getElement(seachElement(uuid));
    }

    @Override
    public void delete(String uuid) {
        removeElement(seachElement(uuid));
    }

    protected int seachElement(String uuid) {
        int index = findElement(uuid);
        if (index >= 0) {
            return index;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract int findElement(String uuid);

    protected abstract void updateElement(Resume resume, int index);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract Resume getElement(int Index);

    protected abstract void removeElement(int index);
}
