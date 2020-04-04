package ru.javawebinar.basejava.model;

import java.util.*;

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
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        contacts = new HashMap<>();
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

    public <T extends AbstractSection> T getSection(SectionType type) {
        return (T) sections.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
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
}
