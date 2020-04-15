package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.storage.serializator.ObjectStreamStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStreamStorage()));
    }
}