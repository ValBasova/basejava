package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isElementExist(Object o) {
        if ((int) o >= 0)
            return true;
        else
            return false;
    }

    @Override
    public void updateElement(Resume resume, Object o) {
        storage[(int) o] = resume;
    }

    @Override
    protected void insertElement(Resume resume, Object o) {
        if (size < STORAGE_LIMIT) {
            insertResume(resume, (int) o);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    public Resume getElement(Object o) {
        return storage[(int) o];
    }

    @Override
    public void removeElement(Object o) {
        removeResume((int) o);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);
}
