package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;


public class MapStorageTest {
    private Storage storage = new MapStorage();

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private Resume resume_1 = new Resume(UUID_1);
    private Resume resume_2 = new Resume(UUID_2);
    private Resume resume_3 = new Resume(UUID_3);

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume_1);
        storage.save(resume_2);
        storage.save(resume_3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume resume_4 = new Resume(UUID_1);
        storage.update(resume_4);
        Assert.assertSame(resume_4, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume());
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        Map<String, Resume> actual = new HashMap<>();
        for (int i = 0; i < array.length; i++)
            actual.put(array[i].getUuid(), array[i]);

        Map<String, Resume> expected = new HashMap<>();
        expected.put(UUID_1, resume_1);
        expected.put(UUID_2, resume_2);
        expected.put(UUID_3, resume_3);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume_1);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(resume_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}