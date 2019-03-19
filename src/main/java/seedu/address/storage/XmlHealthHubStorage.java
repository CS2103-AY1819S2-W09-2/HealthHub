package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.RequestBook;
import seedu.address.model.healthworker.HealthworkerList;

/**
 * A class to access FoodZoom data stored as an XML file.
 */
public class XmlHealthHubStorage implements HealthHubStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlHealthHubStorage.class);

    private Path healthHubFilePath;
    private Optional<HealthworkerList> deliverymenList;
    private Optional<RequestBook> orderBook;

    public XmlHealthHubStorage(Path healthHubFilePath) {
        this.healthHubFilePath = healthHubFilePath;
    }


    @Override
    public Path getHealthHubFilePath() {
        return healthHubFilePath;
    }

    @Override
    public void saveHealthHub(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList)
        throws IOException {
        saveHealthHub(orderBook, healthworkerList, healthHubFilePath);
    }

    /**
     * Saves data to the XML file in the hard disk.
     */
    @Override
    public void saveHealthHub(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList,
                              Path filePath)
        throws IOException {
        requireNonNull(healthworkerList);
        requireNonNull(orderBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveFoodZoomDataToFile(filePath, new XmlHealthHub(orderBook, healthworkerList));
    }

    @Override
    public Optional<ReadOnlyRequestBook> readRequestBook() throws DataConversionException, IOException {
        return readRequestBook(healthHubFilePath);
    }

    /**
     * Similar to {@link #readRequestBook()} ()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyRequestBook> readRequestBook(Path filePath) throws DataConversionException,
        FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("FoodZoom file " + filePath + " not found");
            return Optional.empty();
        }

        XmlHealthHub xmlHealthHub = XmlFileStorage.loadFoodZoomDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlHealthHub.getOrderBook());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public Optional<HealthworkerList> readHealthworkerList() throws DataConversionException, IOException {
        return readHealthworkerList(healthHubFilePath);
    }

    /**
     * Similar to {@link #readHealthworkerList()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<HealthworkerList> readHealthworkerList(Path filePath) throws DataConversionException,
        FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("FoodZoom file " + filePath + " not found");
            return Optional.empty();
        }

        XmlHealthHub xmlHealthHub = XmlFileStorage.loadFoodZoomDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlHealthHub.getDeliverymenList());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }
}
