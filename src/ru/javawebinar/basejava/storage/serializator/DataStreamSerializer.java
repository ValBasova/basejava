package ru.javawebinar.basejava.storage.serializator;

import ru.javawebinar.basejava.model.*;

import java.io.*;
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
            writeCollection(contacts.entrySet(), dos, new MapWriter(), r);

            Map<SectionType, AbstractSection> sections = r.getSections();
            writeCollection(sections.entrySet(), dos, new MapWriter(), r);
        }
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos,
                                     Writer writer, Resume r) throws IOException {
        dos.writeInt(collection.size());
        for (T el : collection) {
            writer.writeElement(el, dos, r);
        }
    }

    private interface Writer<T> {
        void writeElement(T t, DataOutputStream dos, Resume r) throws IOException;
    }

    private static class StringWriter implements Writer<String> {
        @Override
        public void writeElement(String s, DataOutputStream dos, Resume r) throws IOException {
            dos.writeUTF(s);
        }
    }

    private class OrganizatonWriter implements Writer<Organization> {
        @Override
        public void writeElement(Organization org, DataOutputStream dos, Resume r) throws IOException {
            dos.writeUTF(org.getName());
            dos.writeUTF(org.getUrl());
            List<Organization.Position> positions = org.getPositions();
            writeCollection(positions, dos, new PositionWriter(), r);
        }
    }

    private static class PositionWriter implements Writer<Organization.Position> {
        @Override
        public void writeElement(Organization.Position pos, DataOutputStream dos, Resume r) throws IOException {
            dos.writeUTF(pos.getTimeStart().toString());
            dos.writeUTF(pos.getTimeEnd().toString());
            dos.writeUTF(pos.getTitle());
            dos.writeUTF(pos.getDescription());
        }
    }

    private class MapWriter implements Writer<Map.Entry> {
        @Override
        public void writeElement(Map.Entry entry, DataOutputStream dos, Resume r) throws IOException {
            if (entry.getKey() instanceof ContactType) {
                ContactType contactType = (ContactType) entry.getKey();
                dos.writeUTF(contactType.name());
                dos.writeUTF(entry.getValue().toString());
            } else if (entry.getKey() instanceof SectionType) {
                SectionType sectionType = (SectionType) entry.getKey();
                dos.writeUTF(sectionType.name());
                writeSectionValues(sectionType, dos, r);
            }
        }
    }

    private void writeSectionValues(SectionType type, DataOutputStream dos, Resume r) throws IOException {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                TextSection ts = r.getSection(type);
                dos.writeUTF(ts.getText());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection ls = r.getSection(type);
                List<String> list = ls.getTextList();
                writeCollection(list, dos, new StringWriter(), r);
                break;
            case EDUCATION:
            case EXPERIENCE:
                OrganizationSection orgs = r.getSection(type);
                List<Organization> orgList = orgs.getOrganizationList();
                writeCollection(orgList, dos, new OrganizatonWriter(), r);
                break;
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readMap(dis, new ContactMapReader(), resume);
            readMap(dis, new SectionMapReader(), resume);
            return resume;
        }
    }

    private <T> List<T> readCollection(DataInputStream dis, Reader<T> reader, Resume r) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readElement(dis, r));
        }
        return list;
    }

    private interface Reader<T> {
        <T> T readElement(DataInputStream dis, Resume r) throws IOException;
    }

    private class ListReader implements Reader<String> {
        @Override
        public String readElement(DataInputStream dis, Resume r) throws IOException {
            return dis.readUTF();
        }
    }

    private class OrganizatonReader implements Reader<Organization> {
        @Override
        public Organization readElement(DataInputStream dis, Resume r) throws IOException {
            String orgName = dis.readUTF();
            String url = dis.readUTF();
            List positions = readCollection(dis, new PositionReader(), r);
            Organization org = new Organization(orgName, url, positions);
            return org;
        }
    }

    private class PositionReader implements Reader<Organization.Position> {
        @Override
        public Organization.Position readElement(DataInputStream dis, Resume r) throws IOException {
            String timeStart = dis.readUTF();
            String timeEnd = dis.readUTF();
            String title = dis.readUTF();
            String description = dis.readUTF();
            return new Organization.Position(timeStart, timeEnd, title, description);
        }
    }

    private void readMap(DataInputStream dis, MapReader reader, Resume r) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.readMapElement(dis, r);
        }
    }

    private interface MapReader {
        void readMapElement(DataInputStream dis, Resume r) throws IOException;
    }

    private class ContactMapReader implements MapReader {
        @Override
        public void readMapElement(DataInputStream dis, Resume r) throws IOException {
            r.getContacts().put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private class SectionMapReader implements MapReader {
        @Override
        public void readMapElement(DataInputStream dis, Resume r) throws IOException {
            SectionType type = SectionType.valueOf(dis.readUTF());
            readSectionValues(type, dis, r);
        }
    }

    private void readSectionValues(SectionType type, DataInputStream dis, Resume r) throws IOException {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                TextSection ts = r.getSection(type);
                ts.setText(dis.readUTF());
                r.getSections().put(type, ts);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection ls = r.getSection(type);
                ls.setTextList(readCollection(dis, new ListReader(), r));
                r.getSections().put(type, ls);
                break;
            case EDUCATION:
            case EXPERIENCE:
                OrganizationSection orgs = r.getSection(type);
                orgs.setOrganizationList(readCollection(dis, new OrganizatonReader(), r));
                r.getSections().put(type, orgs);
                break;
        }
    }
}
