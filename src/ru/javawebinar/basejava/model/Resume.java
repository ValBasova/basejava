package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVertionUID = 1l;

    public static final Resume EMPTY = new Resume();

    static {
        EMPTY.sections.put(SectionType.OBJECTIVE, TextSection.EMPTY);
        EMPTY.sections.put(SectionType.PERSONAL, TextSection.EMPTY);
        EMPTY.sections.put(SectionType.ACHIEVEMENT, ListSection.EMPTY);
        EMPTY.sections.put(SectionType.QUALIFICATIONS, ListSection.EMPTY);
        EMPTY.sections.put(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
        EMPTY.sections.put(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
    }

    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid,
                  String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
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
