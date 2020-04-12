package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;
    private Serializator serializator;

    protected AbstractPathStorage(String dir, Serializator serializator) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serializator, "serializator must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serializator = serializator;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::removeElement);
        } catch (IOException e) {
            throw new StorageException("Path delete error", e);
        }
    }

    @Override
    public int size() {
        try {
            return Files.list(directory).toArray().length;
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void updateElement(Resume r, Path path) {
        try {
            serializator.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isElementExist(Path path) {
        return !Files.notExists(path);
    }

    @Override
    protected void insertElement(Resume r, Path path) {
        try {
            Files.createFile(path);
            serializator.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return serializator.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void removeElement(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getListResume() {
        List<Resume> list = new ArrayList<>();
        try {
            List<Path> paths = Files.list(directory).collect(Collectors.toList());
            for (Path path : paths) {
                list.add(getElement(path));
            }
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
        return list;
    }
}