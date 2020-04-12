package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    private Serializator serializator;

    protected AbstractFileStorage(File directory, Serializator serializator) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serializator, "serializator must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serializator = serializator;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                removeElement(file);
            }
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateElement(Resume r, File file) {
        try {
            serializator.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected boolean isElementExist(File file) {
        return file.exists();
    }

    @Override
    protected void insertElement(Resume r, File file) {
        try {
            file.createNewFile();
            serializator.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return serializator.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void removeElement(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }

    }

    @Override
    protected List<Resume> getListResume() {
        File[] files = directory.listFiles();
        List<Resume> list = new ArrayList<>();
        assert files != null;
        for (File file : files) {
            Resume r;
            try {
                r = serializator.doRead(new BufferedInputStream(new FileInputStream(file)));
            } catch (IOException e) {
                throw new StorageException("Directory read error", file.getName(), e);
            }
            list.add(r);
        }
        return list;
    }
}