package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Users\\Valery\\Desktop\\basejava\\storage");

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume R1 = new Resume(UUID_1, "Name1");
    private static final Resume R2 = new Resume(UUID_2, "Name2");
    private static final Resume R3 = new Resume(UUID_3, "Name3");
    private static final Resume R4 = new Resume(UUID_4, "Name4");

    static {
        R1.getContacts().put(ContactType.EMAIL, "abcdef@gmail.com");
        R1.getContacts().put(ContactType.PHONE_NUMBER, "123456");
    }

//        TextSection ps_1 = R1.getSection(SectionType.PERSONAL);
//        ps_1.setText("Personal_1)");
//        TextSection os_1 = R1.getSection(SectionType.OBJECTIVE);
//        os_1.setText("Objective_1");
//        ListSection as_1 = R1.getSection(SectionType.ACHIEVEMENT);
//        as_1.setTextList(Arrays.asList("Achievement_0_1", "Achievement 0_2"));
//        ListSection qs_1 = R1.getSection(SectionType.QUALIFICATIONS);
//        qs_1.setTextList(Arrays.asList("Qualigications_1"));
//        Organization org_1_1 = new Organization("Org_1");
//        org_1_1.addPosition("2020-01", "2020-02", "Title_1", "Descriprion_1");
//        OrganizationSection es_1 = R1.getSection(SectionType.EXPERIENCE);
//        es_1.setOrganizationList(Arrays.asList(org_1_1));
//        Organization org_1_2 = new Organization("Org_2");
//        org_1_2.addPosition("2020-01", "2020-03", null, "Description_2");
//        OrganizationSection eds_1 = R1.getSection(SectionType.EDUCATION);
//        eds_1.setOrganizationList(Arrays.asList(org_1_2));
//
//        TextSection ps_2 = R2.getSection(SectionType.PERSONAL);
//        ps_2.setText("Personal_2)");
//        TextSection os_2 = R2.getSection(SectionType.OBJECTIVE);
//        os_2.setText("Objective_2");
//        ListSection as_2 = R2.getSection(SectionType.ACHIEVEMENT);
//        as_2.setTextList(Arrays.asList("Achievement_2"));
//        ListSection qs_2 = R2.getSection(SectionType.QUALIFICATIONS);
//        qs_2.setTextList(Arrays.asList("Qualigications_2"));
//        Organization org_2_1 = new Organization("Org_2");
//        org_2_1.addPosition("2020-01", "2020-02", "Title_2", "Descriprion_2");
//        OrganizationSection es_2 = R2.getSection(SectionType.EXPERIENCE);
//        es_2.setOrganizationList(Arrays.asList(org_2_1));
//        Organization org_2_2 = new Organization("Org_2_2");
//        org_2_2.addPosition("2020-01", "2020-03", null, "Description_2");
//        OrganizationSection eds_2 = R2.getSection(SectionType.EDUCATION);
//        eds_2.setOrganizationList(Arrays.asList(org_2_2));
//
//        TextSection ps_3 = R3.getSection(SectionType.PERSONAL);
//        ps_3.setText("Personal_3)");
//        TextSection os_3 = R3.getSection(SectionType.OBJECTIVE);
//        os_3.setText("Objective_3");
//        ListSection as_3 = R3.getSection(SectionType.ACHIEVEMENT);
//        as_3.setTextList(Arrays.asList("Achievement_3"));
//        ListSection qs_3 = R3.getSection(SectionType.QUALIFICATIONS);
//        qs_3.setTextList(Arrays.asList("Qualigications_3"));
//        Organization org_3_1 = new Organization("Org_3");
//        org_3_1.addPosition("2020-01", "2020-02", "Title_3", "Descriprion_3");
//        OrganizationSection es_3 = R3.getSection(SectionType.EXPERIENCE);
//        es_3.setOrganizationList(Arrays.asList(org_3_1));
//        Organization org_3_2 = new Organization("Org_3_2");
//        org_3_2.addPosition("2020-01", "2020-03", null, "Description_3");
//        OrganizationSection eds_3 = R3.getSection(SectionType.EDUCATION);
//        eds_3.setOrganizationList(Arrays.asList(org_3_2));
//
//        TextSection ps_4 = R4.getSection(SectionType.PERSONAL);
//        ps_4.setText("Personal_4)");
//        TextSection os_4 = R4.getSection(SectionType.OBJECTIVE);
//        os_4.setText("Objective_4");
//        ListSection as_4 = R4.getSection(SectionType.ACHIEVEMENT);
//        as_4.setTextList(Arrays.asList("Achievement_4"));
//        ListSection qs_4 = R4.getSection(SectionType.QUALIFICATIONS);
//        qs_4.setTextList(Arrays.asList("Qualigications_4"));
//        Organization org_4_1 = new Organization("Org_4");
//        org_4_1.addPosition("2020-01", "2020-02", "Title_4", "Descriprion_4");
//        OrganizationSection es_4 = R4.getSection(SectionType.EXPERIENCE);
//        es_4.setOrganizationList(Arrays.asList(org_4_1));
//        Organization org_4_2 = new Organization("Org_4_2");
//        org_4_2.addPosition("2020-01", "2020-03", null, "Description_4");
//        OrganizationSection eds_4 = R4.getSection(SectionType.EDUCATION);
//        eds_4.setOrganizationList(Arrays.asList(org_4_2));
//    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> actual = storage.getAllSorted();
        List<Resume> expected = Arrays.asList(R1, R2, R3);
        assertArrayEquals(expected.toArray(), actual.toArray());
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1, "dummy");
        resume.getContacts().put(ContactType.EMAIL, "12345@gmail.com");
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void save() throws Exception {
        storage.save(R4);
        assertEquals(4, storage.size());
        assertEquals(R4, storage.get(R4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(R1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        assertEquals(R1, storage.get(R1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertEquals(R1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}