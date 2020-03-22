package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public List<Resume> getAllSorted() {
        Resume[] resumes = Arrays.copyOfRange(storage, 0, size);
        List<Resume> listResume = new ArrayList(Arrays.asList(resumes));
        listResume.sort(nameComparator);
        return listResume;
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
    protected boolean isElementExist(Object index) {
        return (int) index >= 0;
    }

    @Override
    public void updateElement(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    protected void insertElement(Resume resume, Object index) {
        if (size < STORAGE_LIMIT) {
            insertResume(resume, (int) index);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    public Resume getElement(Object index) {
        return storage[(int) index];
    }

    @Override
    public void removeElement(Object index) {
        removeResume((int) index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);
}
