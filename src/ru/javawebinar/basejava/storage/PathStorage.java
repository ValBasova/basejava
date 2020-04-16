package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializator.Serializator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private Serializator serializator;

    protected PathStorage(String dir, Serializator serializator) {
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
        getStreamList().forEach(this::removeElement);
    }

    @Override
    public int size() {
        return (int) getStreamList().count();
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
        return Files.exists(path);
    }

    @Override
    protected void insertElement(Resume r, Path path) {
        try {
            Files.createFile(path);
            updateElement(r, path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path.toAbsolutePath(), path.getFileName().toString(), e);
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
        return getStreamList().map(this::getElement).collect(Collectors.toList());
    }

    private Stream<Path> getStreamList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }
}