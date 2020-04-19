package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.storage.serializator.ObjectStreamStorage;

public class ObjectPathStorageTest extends AbstractStorageTest{
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStreamStorage()));
    }
}