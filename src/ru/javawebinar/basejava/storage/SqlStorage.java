package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.sqlExecute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
                    Resume resume;
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =? ")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        resume = new Resume(uuid, rs.getString("full_name"));
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact c WHERE c.resume_uuid =?")) {
                        ps.setString(1, resume.getUuid());
                        ResultSet resultSet = ps.executeQuery();
                        while (resultSet.next()) {
                            addContactResume(resultSet, resume);
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid =?")) {
                        ps.setString(1, resume.getUuid());
                        ResultSet resultSet = ps.executeQuery();
                        while (resultSet.next()) {
                            addSectionResume(resultSet, resume);
                        }
                    }
                    return resume;
                }
        );
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name = ? WHERE r.uuid =?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact c WHERE c.resume_uuid =?;")) {
                        ps.setString(1, r.getUuid());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section s WHERE s.resume_uuid =?;")) {
                        ps.setString(1, r.getUuid());
                        ps.execute();
                    }
                    sqlInsertContacts(r, conn);
                    sqlInsertSections(r, conn);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    sqlInsertContacts(r, conn);
                    sqlInsertSections(r, conn);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.sqlExecute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
                    Map<String, Resume> map = new LinkedHashMap<>();
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name,uuid")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("uuid");
                            Resume resume = map.get(uuid);
                            if (resume == null) {
                                resume = new Resume(uuid, rs.getString("full_name"));
                                map.put(uuid, resume);
                            }
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact c ORDER BY resume_uuid")) {
                        ResultSet resultSet = ps.executeQuery();
                        while (resultSet.next()) {
                            addContactResume(resultSet, map.get(resultSet.getString("resume_uuid")));
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section s ORDER BY resume_uuid")) {
                        ResultSet resultSet = ps.executeQuery();
                        while (resultSet.next()) {
                            addSectionResume(resultSet, map.get(resultSet.getString("resume_uuid")));
                        }
                    }
                    return new ArrayList<>(map.values());
                }
        );
    }

    @Override
    public int size() {
        return sqlHelper.sqlExecute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
            return 0;
        });
    }

    private void sqlInsertContacts(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void sqlInsertSections(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(1, r.getUuid());
                        ps.setString(2, sectionType.name());
                        TextSection ts = r.getSection(sectionType);
                        ps.setString(3, ts.getText());
                        ps.addBatch();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = r.getSection(sectionType);
                        List<String> list = ls.getTextList();
                        if (list.size() > 0) {
                            ps.setString(1, r.getUuid());
                            ps.setString(2, sectionType.name());
                            ps.setString(3, String.join("\n", list));
                            ps.addBatch();
                        }
                        break;
                }
                ps.executeBatch();
            }
        }
    }

    private void addContactResume(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        String type = rs.getString("type");
        if (type != null) {
            ContactType contactType = ContactType.valueOf(type);
            r.getContacts().put(contactType, value);
        }
    }

    private void addSectionResume(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        String type = rs.getString("type");
        if (type != null) {
            SectionType sectionType = SectionType.valueOf(type);
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    TextSection ts = r.getSection(sectionType);
                    ts.setText(value);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ListSection ls = r.getSection(sectionType);
                    ls.setTextList(Arrays.asList(value.split("\n")));
                    break;
            }
        }
    }
}