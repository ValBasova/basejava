package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private static final Comparator<Resume> NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).
            thenComparing(Resume::getUuid);

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> storage = getListResume();
        storage.sort(NAME_COMPARATOR);
        return storage;
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        updateElement(resume, searchElement(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK key = getSearchKey(resume.getUuid());
        if (!isElementExist(key)) {
            insertElement(resume, key);
        } else {
            LOG.warning("Resume " + resume + " already exist");
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return getElement(searchElement(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        removeElement(searchElement(uuid));
    }

    private SK searchElement(String uuid) {
        SK key = getSearchKey(uuid);
        if (isElementExist(key)) {
            return key;
        } else {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract List<Resume> getListResume();

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isElementExist(SK key);

    protected abstract void updateElement(Resume resume, SK key);

    protected abstract void insertElement(Resume resume, SK key);

    protected abstract Resume getElement(SK key);

    protected abstract void removeElement(SK key);
}
