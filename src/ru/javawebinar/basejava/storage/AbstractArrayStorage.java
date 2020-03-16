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
    public void updateElement(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        if (size < STORAGE_LIMIT) {
            insertResume(resume, index);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    public Resume getElement(int index) {
        return storage[index];
    }

    @Override
    public void removeElement(int index) {
        removeResume(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);
}
