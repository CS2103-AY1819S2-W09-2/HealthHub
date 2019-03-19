package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.healthworker.HealthworkerList;

/**
 * Represents storage for FoodZoom
 */
public interface HealthHubStorage {
    Path getHealthHubFilePath();

    Optional<ReadOnlyRequestBook> readRequestBook() throws DataConversionException, IOException;

    Optional<ReadOnlyRequestBook> readRequestBook(Path filePath) throws DataConversionException,
        IOException;

    Optional<HealthworkerList> readHealthworkerList() throws DataConversionException, IOException;

    Optional<HealthworkerList> readHealthworkerList(Path filePath) throws DataConversionException,
        IOException;

    void saveHealthHub(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList) throws IOException;

    void saveHealthHub(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList, Path filePath)
        throws IOException;
}
