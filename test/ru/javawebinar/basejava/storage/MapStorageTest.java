package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;


public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        Map<String, Resume> actual = new HashMap<>();
        for (int i = 0; i < array.length; i++)
            actual.put(array[i].getUuid(), array[i]);

        Map<String, Resume> expected = new HashMap<>();
        expected.put("uuid1", storage.get("uuid1"));
        expected.put("uuid2", storage.get("uuid2"));
        expected.put("uuid3", storage.get("uuid3"));
        Assert.assertEquals(expected, actual);
    }
}