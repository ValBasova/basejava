package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    private final String uuid;
    private String fullName;
    private Map<ContactType, String> contacts;
    private Map<SectionType, AbstractSection> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid,
                  String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
        contacts = new HashMap<>();
        for (ContactType type : ContactType.values()) {
            contacts.put(type, null);
        }
        sections = new HashMap<>();
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    sections.put(type, new TextSection());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    sections.put(type, new ListSection());
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    sections.put(type, new OrganizationSection());
                    break;
            }
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Resume {" + "\n" +
                "uuid = '" + uuid + '\'' + "," + "\n" +
                "fullName = '" + fullName + '\'' + "," + "\n" +
                "contacts = " + contacts + "," + "\n" +
                "sections = " + sections + "\n" +
                '}';
    }

    public <T extends AbstractSection> T  getSection(SectionType type) {
        return (T)sections.get(type);
    }

    public void fillContacts(ContactType type, String content){
        contacts.put(type, content);
    }
}
