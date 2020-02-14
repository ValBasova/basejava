import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int index = 0;

    void save(Resume resume) {
        storage[index] = resume;
        index++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < index; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
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
        for (int i = 0; i < index; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j < index; j++) {
                    storage[j] = storage[j + 1];
                }
                break;
            }
        }
        index--;
    }

    void clear() {
        for (int i = 0; i < index; i++) {
            storage[i] = null;
        }
        index = 0;
    }

    int size() {
        return index;
    }
}