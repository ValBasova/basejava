package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializator.Serializator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;

    private Serializator serializator;

    protected FileStorage(File directory, Serializator serializator) {
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
        File[] files = getFileList();
        for (File file : files) {
            removeElement(file);
        }
    }

    @Override
    public int size() {
        return getFileList().length;
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
            updateElement(r, file);
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
        File[] files = getFileList();
        List<Resume> list = new ArrayList<>();
        for (File file : files) {
            list.add(getElement(file));
        }
        return list;
    }

    private File[] getFileList() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory null error");
        }
        return files;
    }
}