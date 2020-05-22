package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File(getHomeDir(),"config\\resumes.properties");
    private static final Config INSTANCE = new Config();

    private Properties props = new Properties();
    private File storageDir;
    private String db_url;
    private String db_user;
    private String db_password;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            db_url = props.getProperty("db.url");
            db_user = props.getProperty("db.user");
            db_password = props.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    private static File getHomeDir() {
        String prop = System.getProperty("homeDir");
        File homeDir = new File(prop == null ? "." : prop);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDb_url() {
        return db_url;
    }

    public String getDb_user() {
        return db_user;
    }

    public String getDb_password() {
        return db_password;
    }
}
