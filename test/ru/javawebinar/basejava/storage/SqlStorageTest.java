package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest{
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDb_url(), Config.get().getDb_user(), Config.get().getDb_password()));
    }
}