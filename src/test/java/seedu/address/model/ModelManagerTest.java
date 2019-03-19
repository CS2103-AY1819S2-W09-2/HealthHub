package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.address.testutil.TypicalDeliverymen.CHIKAO;
import static seedu.address.testutil.TypicalDeliverymen.RAJUL;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BENSON;
import static seedu.address.testutil.user.TypicalUsers.ALICE_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.CARL_MANAGER;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.healthworker.HealthworkerList;
import seedu.address.model.order.NameContainsKeywordsPredicate;
import seedu.address.model.user.User;
import seedu.address.testutil.DeliverymenListBuilder;
import seedu.address.testutil.OrderBookBuilder;
import seedu.address.testutil.user.UserBuilder;
import seedu.address.testutil.user.UsersListBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasOrder(null);
    }

    @Test
    public void hasOrder_orderNotInOrderBook_returnsFalse() {
        assertFalse(modelManager.hasOrder(ALICE));
    }

    @Test
    public void hasOrder_orderInOrderBook_returnsTrue() {
        modelManager.addOrder(ALICE);
        assertTrue(modelManager.hasOrder(ALICE));
    }

    @Test
    public void getFilteredOrderList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredOrderList().remove(0);
    }

    @Test
    public void isUserLoggedIn_isLoggedIn_returnsTrue() {
        User alice = new UserBuilder(ALICE_MANAGER).build();
        modelManager.storeUserInSession(alice);
        assertTrue(modelManager.isUserLoggedIn());
    }

    @Test
    public void isUserLoggedIn_isNotLoggedIn_returnsFalse() {
        assertFalse(modelManager.isUserLoggedIn());
    }

    @Test
    public void getLoggedInUserDetails_isLoggedIn_returnsNotNull() {
        User alice = new UserBuilder(ALICE_MANAGER).build();
        modelManager.storeUserInSession(alice);
        assertNotNull(modelManager.getLoggedInUserDetails());
    }

    @Test
    public void getLoggedInUserDetails_isNotLoggedIn_returnsNull() {
        assertNull(modelManager.getLoggedInUserDetails());
    }


    @Test
    public void equals() {
        RequestBook orderBook = new OrderBookBuilder().withOrder(ALICE).withOrder(BENSON).build();
        RequestBook differentOrderBook = new RequestBook();
        HealthworkerList healthworkerList = new DeliverymenListBuilder().withDeliveryman(RAJUL).withDeliveryman(CHIKAO)
            .build();
        HealthworkerList differentHealthworkerList = new HealthworkerList();
        UsersList usersList = new UsersListBuilder().withUser(ALICE_MANAGER).withUser(CARL_MANAGER).build();
        UsersList differentUsersList = new UsersList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(orderBook, usersList, healthworkerList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(orderBook, usersList, healthworkerList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different RequestBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentOrderBook, differentUsersList,
            differentHealthworkerList, userPrefs)));
        assertFalse(modelManager.equals(new ModelManager(differentOrderBook, usersList,
            healthworkerList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredOrderList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(orderBook, usersList, healthworkerList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFoodZoomFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(orderBook, usersList,
            healthworkerList, differentUserPrefs)));
    }
}
