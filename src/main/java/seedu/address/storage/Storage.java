package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.HealthHubChangedEvent;
import seedu.address.commons.events.model.UsersListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliveryman.HealthworkerList;
import seedu.address.storage.user.UsersListStorage;

/**
 * API of the Storage component
 */
public interface Storage extends FoodZoomStorage, UserPrefsStorage, UsersListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getUsersListFilePath();

    @Override
    Optional<ReadOnlyUsersList> readUsersList() throws DataConversionException, IOException;

    @Override
    void saveUsersList(ReadOnlyUsersList usersList) throws IOException;

    /**
     * Saves the current version of the UsersList to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleUsersListChangedEvent(UsersListChangedEvent ulce);

    @Override
    Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException, IOException;

    @Override
    Optional<HealthworkerList> readDeliverymenList() throws DataConversionException, IOException;

    @Override
    Optional<HealthworkerList> readDeliverymenList(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveFoodZoom(ReadOnlyOrderBook orderBook, HealthworkerList healthworkerList) throws IOException;

    @Override
    void saveFoodZoom(ReadOnlyOrderBook orderBook, HealthworkerList healthworkerList, Path filePath) throws IOException;

    void handleFoodZoomChangedEvent(HealthHubChangedEvent fzce);
}
