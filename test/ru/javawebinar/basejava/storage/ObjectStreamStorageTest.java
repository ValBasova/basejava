package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializator.ObjectStreamStorage;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}
