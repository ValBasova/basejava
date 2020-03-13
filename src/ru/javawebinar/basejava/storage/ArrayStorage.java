package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findElement(String uuid) {
        for (int index = 0; index < size; index++) {
            if (storage[index].getUuid() == uuid) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected void insertArrayElement(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void removeArrayElement(int index) {
        storage[index] = storage[size - 1];
    }
}