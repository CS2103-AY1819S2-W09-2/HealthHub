package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.HealthHubChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.RequestBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UserPrefs;
import seedu.address.model.healthworker.HealthworkerList;
import seedu.address.storage.user.XmlUsersListStorage;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlUsersListStorage usersListStorage = new XmlUsersListStorage(getTempFilePath("users"));
        XmlHealthHubStorage foodZoomStorage = new XmlHealthHubStorage(getTempFilePath("foodzoom"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(usersListStorage, foodZoomStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void foodZoomReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlHealthHubStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlFoodZoomStorageTest} class.
         */
        RequestBook original = getTypicalOrderBook();
        HealthworkerList healthworkerList = getTypicalDeliverymenList();
        storageManager.saveHealthHub(original, healthworkerList);
        ReadOnlyRequestBook ordersRetrieved = storageManager.readRequestBook().get();
        assertEquals(original, new RequestBook(ordersRetrieved));
        HealthworkerList dmenRetrieved = storageManager.readHealthworkerList().get();
        assertEquals(healthworkerList, new HealthworkerList(dmenRetrieved));
    }

    @Test
    public void getFoodZoomFilePath() {
        assertNotNull(storageManager.getHealthHubFilePath());
    }

    @Test
    public void handleOrderBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlUsersListStorageExceptionThrowingStub(Paths.get("dummy")),
            new XmlHealthHubStorageExceptionThrowingStub(Paths.get("dummy2")),
            new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleFoodZoomChangedEvent(new HealthHubChangedEvent(new RequestBook(), new HealthworkerList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlHealthHubStorageExceptionThrowingStub extends XmlHealthHubStorage {

        public XmlHealthHubStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveHealthHub(ReadOnlyRequestBook orderBook, HealthworkerList healthworkerList, Path filePath) throws
            IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlUsersListStorageExceptionThrowingStub extends XmlUsersListStorage {

        public XmlUsersListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveUsersList(ReadOnlyUsersList usersList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
