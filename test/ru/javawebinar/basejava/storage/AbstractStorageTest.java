package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private Resume resume_1 = new Resume(UUID_1, "Анна");
    private Resume resume_2 = new Resume(UUID_2, "Гена");
    private Resume resume_3 = new Resume(UUID_3, "Зена");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume_1);
        storage.save(resume_2);
        storage.save(resume_3);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> actual = storage.getAllSorted();
        Resume[] resumes = new Resume[] {resume_1, resume_2, resume_3};
        List<Resume> expected = Arrays.asList(resumes);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected.size(), actual.size());
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
        Resume resume_4 = new Resume(UUID_1, "Сеня");
        storage.update(resume_4);
        Assert.assertSame(resume_4, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("Бася"));
    }

    @Test
    public void save() throws Exception {
        Resume resume_4 = new Resume("Бася");
        storage.save(resume_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resume_4, storage.get(resume_4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        Assert.assertEquals(resume_1, storage.get(resume_1.getUuid()));
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