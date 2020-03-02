package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume, int index) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void removeElement(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int index = 0; index < size; index++) {
            if (storage[index].getUuid().equals(uuid)) {
                return index;
            }
        }
        return -1;
    }
}