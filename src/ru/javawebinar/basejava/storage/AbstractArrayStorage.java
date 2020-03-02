package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println(resume + " updated!");
        } else {
            printErrorIfNotExist(resume.getUuid());
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT) {
                insertElement(resume, index);
                size++;
            } else {
                System.out.println("The array is full");
            }
        } else {
            System.out.println("ERROR: " + resume + " does already present!");
        }
    }

    protected abstract void insertElement(Resume resume, int index);

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        printErrorIfNotExist(uuid);
        return null;
    }

    protected abstract int getIndex(String uuid);

    protected void printErrorIfNotExist(String uuid) {
        System.out.println("ERROR: " + uuid + " does not present!");
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            removeElement(index);
            storage[size - 1] = null;
            size--;
        } else {
            printErrorIfNotExist(uuid);
        }
    }

    protected abstract void removeElement(int index);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }
}
