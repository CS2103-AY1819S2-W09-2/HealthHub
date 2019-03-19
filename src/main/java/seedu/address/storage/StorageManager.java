package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.HealthHubChangedEvent;
import seedu.address.commons.events.model.UsersListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UserPrefs;
import seedu.address.model.healthworker.HealthworkerList;
import seedu.address.storage.user.UsersListStorage;

/**
 * Manages storage of RequestBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HealthHubStorage healthHubStorage;
    private UserPrefsStorage userPrefsStorage;
    private UsersListStorage usersListStorage;

    public StorageManager(UsersListStorage usersListStorage, HealthHubStorage healthHubStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.healthHubStorage = healthHubStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.usersListStorage = usersListStorage;
    }

    // ================ UsersList methods ==============================

    @Override
    public Path getUsersListFilePath() {
        return usersListStorage.getUsersListFilePath();
    }

    @Override
    public Optional<ReadOnlyUsersList> readUsersList() throws DataConversionException, IOException {
        return readUsersList(usersListStorage.getUsersListFilePath());
    }

    @Override
    public Optional<ReadOnlyUsersList> readUsersList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return usersListStorage.readUsersList(filePath);
    }

    @Override
    public void saveUsersList(ReadOnlyUsersList usersList) throws IOException {
        saveUsersList(usersList, usersListStorage.getUsersListFilePath());
    }

    @Override
    public void saveUsersList(ReadOnlyUsersList usersList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        usersListStorage.saveUsersList(usersList, filePath);
    }

    @Override
    @Subscribe
    public void handleUsersListChangedEvent(UsersListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveUsersList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    @Override
    public Path getHealthHubFilePath() {
        return healthHubStorage.getHealthHubFilePath();
    }

    //================================ data read methods ======================================
    @Override
    public Optional<ReadOnlyRequestBook> readRequestBook() throws DataConversionException, IOException {
        return readRequestBook(healthHubStorage.getHealthHubFilePath());
    }

    @Override
    public Optional<ReadOnlyRequestBook> readRequestBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return healthHubStorage.readRequestBook(filePath);
    }

    @Override
    public Optional<HealthworkerList> readHealthworkerList() throws DataConversionException, IOException {
        return readHealthworkerList(healthHubStorage.getHealthHubFilePath());
    }

    @Override
    public Optional<HealthworkerList> readHealthworkerList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return healthHubStorage.readHealthworkerList(filePath);
    }

    //================================ data save methods ======================================

    @Override
    public void saveHealthHub(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList) throws IOException {
        saveHealthHub(orderBook, healthworkerList, healthHubStorage.getHealthHubFilePath());
    }

    @Override
    public void saveHealthHub(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList, Path filePath) throws
        IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        healthHubStorage.saveHealthHub(orderBook, healthworkerList, filePath);
    }

    @Override
    @Subscribe
    public void handleFoodZoomChangedEvent(HealthHubChangedEvent healthHubChangedEvent) {
        logger.info(LogsCenter.getEventHandlingLogMessage(healthHubChangedEvent, "Local data changed, saving to file"));
        try {
            saveHealthHub(healthHubChangedEvent.orderBook, healthHubChangedEvent.healthworkerList);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
