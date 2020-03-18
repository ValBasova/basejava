package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;


public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    public void getAll() {
        Resume[] actual = storage.getAll();
        Resume[] expected = new Resume[]{storage.get("uuid1"), storage.get("uuid2"), storage.get("uuid3")};
        Assert.assertArrayEquals(expected, actual);
    }
}