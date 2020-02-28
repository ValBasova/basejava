package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            index = -index - 1;
            if (size < STORAGE_LIMIT) {
                for (int i = size - 1; i >= index; i--) {
                    storage[i + 1] = storage[i];
                }
                storage[index] = resume;
                size++;
            } else {
                System.out.println("The array is full");
            }
        } else {
            System.out.println("ERROR: " + resume + " does already present!");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            storage[size - 1] = null;
            size--;
        } else {
            printErrorIfNotExist(uuid);
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
