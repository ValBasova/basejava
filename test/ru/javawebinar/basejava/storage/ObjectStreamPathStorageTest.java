package ru.javawebinar.basejava.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{
    public ObjectStreamPathStorageTest() {
        super(new AbstractPathStorage(STORAGE_DIR.getPath(), new ObjectStreamPathStorage()));
    }
}