package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        printErrorIfNotExist(uuid);
        return null;
    }

    protected abstract int getIndex(String uuid);

    private void printErrorIfNotExist(String uuid) {
        System.out.println("ERROR: " + uuid + " does not present!");
    }

    public int size() {
        return size;
    }
}
