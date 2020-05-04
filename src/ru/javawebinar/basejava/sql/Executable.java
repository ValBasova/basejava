package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Executable<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
