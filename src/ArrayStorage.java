import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int index = 0;

    void save(Resume resume) {
        storage[index] = resume;
        ++index;
    }

    Resume get(String uuid) {
        Resume[] filledStorage = getAll();
        for (int i = 0; i < filledStorage.length; i++) {
            if (filledStorage[i].uuid.equals(uuid)) {
                return filledStorage[i];
            }
        }
        return null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, index);
    }

    void delete(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j < size(); j++) {
                    storage[j] = storage[j + 1];
                }
                break;
            }
        }
        --index;
    }

    void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
        index = 0;
    }

    int size() {
        return getAll().length;
    }
}