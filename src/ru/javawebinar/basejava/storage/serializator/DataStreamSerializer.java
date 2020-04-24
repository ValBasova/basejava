package ru.javawebinar.basejava.storage.serializator;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializator {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(contacts.entrySet(), dos, entry -> {
                ContactType contactType = entry.getKey();
                dos.writeUTF(contactType.name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();
            writeCollection(sections.entrySet(), dos, entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection ts = r.getSection(sectionType);
                        dos.writeUTF(ts.getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = r.getSection(sectionType);
                        List<String> list = ls.getTextList();
                        writeCollection(list, dos, dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        OrganizationSection orgs = r.getSection(sectionType);
                        List<Organization> orgList = orgs.getOrganizationList();
                        writeCollection(orgList, dos, org -> {
                            dos.writeUTF(org.getName());
                            dos.writeUTF(org.getUrl());
                            List<Organization.Position> positions = org.getPositions();
                            writeCollection(positions, dos, position -> {
                                        dos.writeUTF(position.getTimeStart().toString());
                                        dos.writeUTF(position.getTimeEnd().toString());
                                        dos.writeUTF(position.getTitle());
                                        dos.writeUTF(position.getDescription());
                                    }
                            );
                        });
                        break;
                }
            });
        }
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos,
                                     Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T el : collection) {
            writer.writeElement(el);
        }
    }

    private interface Writer<T> {
        void writeElement(T t) throws IOException;
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readMap(dis, () -> {
                resume.getContacts().put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });
            readMap(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection ts = resume.getSection(type);
                        ts.setText(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = resume.getSection(type);
                        ls.setTextList(readList(dis, dis::readUTF));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        OrganizationSection orgs = resume.getSection(type);
                        orgs.setOrganizationList(readList(dis, () -> new Organization(
                                dis.readUTF(),
                                dis.readUTF(),
                                readList(dis, () -> new Organization.Position(
                                        YearMonth.parse(dis.readUTF()),
                                        YearMonth.parse(dis.readUTF()),
                                        dis.readUTF(),
                                        dis.readUTF())))));
                        break;
                }
            });
            return resume;
        }
    }

    private <T> List<T> readList(DataInputStream dis, ListReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readElement());
        }
        return list;
    }

    private <T> void readMap(DataInputStream dis, MapReader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private interface MapReader {
        void read() throws IOException;
    }

    private interface ListReader<T> {
        T readElement() throws IOException;

    }
}
