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
public interface FoodZoomStorage {
    Path getFoodZoomFilePath();

    Optional<ReadOnlyRequestBook> readOrderBook() throws DataConversionException, IOException;

    Optional<ReadOnlyRequestBook> readOrderBook(Path filePath) throws DataConversionException,
        IOException;

    Optional<HealthworkerList> readDeliverymenList() throws DataConversionException, IOException;

    Optional<HealthworkerList> readDeliverymenList(Path filePath) throws DataConversionException,
        IOException;

    void saveFoodZoom(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList) throws IOException;

    void saveFoodZoom(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList, Path filePath)
        throws IOException;
}
