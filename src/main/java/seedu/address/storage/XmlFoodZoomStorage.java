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
public class XmlFoodZoomStorage implements FoodZoomStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlFoodZoomStorage.class);

    private Path foodZoomFilePath;
    private Optional<HealthworkerList> deliverymenList;
    private Optional<RequestBook> orderBook;

    public XmlFoodZoomStorage(Path foodZoomFilePath) {
        this.foodZoomFilePath = foodZoomFilePath;
    }


    @Override
    public Path getFoodZoomFilePath() {
        return foodZoomFilePath;
    }

    @Override
    public void saveFoodZoom(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList)
        throws IOException {
        saveFoodZoom(orderBook, healthworkerList, foodZoomFilePath);
    }

    /**
     * Saves data to the XML file in the hard disk.
     */
    @Override
    public void saveFoodZoom(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList,
                             Path filePath)
        throws IOException {
        requireNonNull(healthworkerList);
        requireNonNull(orderBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveFoodZoomDataToFile(filePath, new XmlHealthHub(orderBook, healthworkerList));
    }

    @Override
    public Optional<ReadOnlyRequestBook> readOrderBook() throws DataConversionException, IOException {
        return readOrderBook(foodZoomFilePath);
    }

    /**
     * Similar to {@link #readOrderBook()} ()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyRequestBook> readOrderBook(Path filePath) throws DataConversionException,
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
    public Optional<HealthworkerList> readDeliverymenList() throws DataConversionException, IOException {
        return readDeliverymenList(foodZoomFilePath);
    }

    /**
     * Similar to {@link #readDeliverymenList()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<HealthworkerList> readDeliverymenList(Path filePath) throws DataConversionException,
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
