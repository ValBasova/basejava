package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int index = 0;

    public void update(Resume resume) {
        int i = check(resume.getUuid());
        if (i >= 0) {
            storage[i] = resume;
            System.out.println(resume + " updated!");
        } else {
            printErrorResumeNotPresent(resume.getUuid());
        }
    }

    public int check(String uuid) {
        for (int i = 0; i < index; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void printErrorResumeNotPresent(String uuid) {
        System.out.println("ERROR: " + uuid + " does not present!");
    }

    public void save(Resume resume) {
        int i = check(resume.getUuid());
        if (i < 0) {
            if (index < storage.length) {
                storage[index] = resume;
                index++;
            } else {
                System.out.println("The array is full");
            }
        } else {
            System.out.println("ERROR: " + resume + " does already present!");
        }
    }

    public Resume get(String uuid) {
        int i = check(uuid);
        if (i >= 0) {
            return storage[i];
        } else {
            printErrorResumeNotPresent(uuid);
            return null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, index);
    }

    public void delete(String uuid) {
        int i = check(uuid);
        if (i >= 0) {
            storage[i] = storage[index - 1];
            storage[index - 1] = null;
            index--;
        } else {
            printErrorResumeNotPresent(uuid);
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, index - 1, null);
        index = 0;
    }

    public int size() {
        return index;
    }
}