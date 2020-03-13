package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int index = findElement(resume.getUuid());
        if (index >= 0) {
            updateElement(resume, index);
            System.out.println(resume + " updated!");
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
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
        int index = findElement(uuid);
        if (index >= 0) {
            return getElement(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = findElement(uuid);
        if (index >= 0) {
            removeElement(index);
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
