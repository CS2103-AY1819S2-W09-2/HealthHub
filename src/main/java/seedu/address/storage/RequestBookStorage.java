package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.RequestBook;

/**
 * Represents a storage for {@link RequestBook}.
 */
public interface RequestBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRequestBookFilePath();

    /**
     * Returns RequestBook data as a {@link ReadOnlyRequestBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRequestBook> readReqeustBook() throws DataConversionException, IOException;

    /**
     * @see #getRequestBookFilePath()
     */
    Optional<ReadOnlyRequestBook> readReqeustBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRequestBook} to the storage.
     *
     * @param orderBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRequestBook(ReadOnlyRequestBook orderBook) throws IOException;

    /**
     * @see #saveRequestBook(ReadOnlyRequestBook)
     */
    void saveRequestBook(ReadOnlyRequestBook orderBook, Path filePath) throws IOException;

}
