package seedu.address.storage.healthworker;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.healthworker.HealthworkerList;

/**
 * Represents the Storage interface for HealthworkerList
 */
public interface HealthworkerListStorage {
    Path getDeliverymenListFilePath();

    /**
     * Returns a list of healthworker as a {@link HealthworkerList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<HealthworkerList> readDeliverymenList() throws DataConversionException, IOException;

    /**
     * @see #getDeliverymanFilePath()
     */
    Optional<HealthworkerList> readDeliverymenList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link HealthworkerList} to the storage.
     * @param deliverymanList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDeliverymenList(HealthworkerList deliverymanList) throws IOException;

    /**
     * @see #saveDeliverymenList(HealthworkerList)
     */
    void saveDeliverymenList(HealthworkerList deliverymanList, Path filePath) throws IOException;
}
