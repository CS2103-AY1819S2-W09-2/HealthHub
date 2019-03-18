package seedu.address.logic.commands.healthworker;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.deliveryman.Healthworker;
import seedu.address.model.deliveryman.HealthworkerList;
import seedu.address.model.order.Order;
import seedu.address.model.user.User;
import seedu.address.testutil.DeliverymanBuilder;

/**
 * Contains tests related to the AddHealthWorkerCommand class.
 */
public class AddHealthWorkerCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddHealthWorkerCommand(null);
    }

    @Test
    public void execute_deliverymanAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDeliverymanAdded modelStub = new ModelStubAcceptingDeliverymanAdded();
        Healthworker validHealthworker = new DeliverymanBuilder().withName("Linus").build();

        CommandResult commandResults = new AddHealthWorkerCommand(validHealthworker).execute(modelStub, commandHistory);

        assertEquals(String.format(AddHealthWorkerCommand.MESSAGE_SUCCESS, validHealthworker),
            commandResults.feedbackToUser);
        assertEquals(Arrays.asList(validHealthworker), modelStub.deliverymenAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() throws Exception {
        Healthworker validHealthworker = new DeliverymanBuilder().withName("Matthew").build();
        AddHealthWorkerCommand addHealthWorkerCommand = new AddHealthWorkerCommand(validHealthworker);
        ModelStub modelStub = new ModelStubWithDeliveryman(validHealthworker);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddHealthWorkerCommand.MESSAGE_DUPLICATE_DELIVERYMAN);
        addHealthWorkerCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Healthworker matthew = new DeliverymanBuilder().withName("Matthew").build();
        Healthworker linus = new DeliverymanBuilder().withName("Linus").build();
        AddHealthWorkerCommand addMatthewCommand = new AddHealthWorkerCommand(matthew);
        AddHealthWorkerCommand addLinusCommand = new AddHealthWorkerCommand(linus);

        // same object -> returns true
        assertTrue(addMatthewCommand.equals(addMatthewCommand));

        // same values -> returns true
        AddHealthWorkerCommand addMatthewCommandCopy = new AddHealthWorkerCommand(matthew);
        assertTrue(addMatthewCommand.equals(addMatthewCommandCopy));

        // different types -> returns false
        assertFalse(addMatthewCommand.equals(1));

        // null -> returns false
        assertFalse(addMatthewCommand.equals(null));

        // different common -> returns false
        assertFalse(addMatthewCommand.equals(addLinusCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addOrder(Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeliveryman(Healthworker healthworker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyOrderBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetDeliverymenData(HealthworkerList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyOrderBook getOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HealthworkerList getDeliverymenList() {
            throw new AssertionError(" This message should not be called");
        }

        @Override
        public boolean hasOrder(Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOrder(Order target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateOrder(Order target, Order editedOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitUsersList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredUsersList(Predicate<User> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<User> getFilteredUsersList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isRegisteredUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUsersList getUsersList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isUserLoggedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void storeUserInSession(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public User getLoggedInUserDetails() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearUserInSession() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDeliveryman(Healthworker person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeliveryman(Healthworker target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDeliveryman(Healthworker target, Healthworker editedHealthworker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Healthworker> getFilteredDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeliverymenList(Predicate<Healthworker> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single request.
     */
    private class ModelStubWithDeliveryman extends ModelStub {
        private final Healthworker healthworker;

        ModelStubWithDeliveryman(Healthworker healthworker) {
            requireNonNull(healthworker);
            this.healthworker = healthworker;
        }

        @Override
        public boolean hasDeliveryman(Healthworker healthworker) {
            requireNonNull(healthworker);
            return this.healthworker.isSameDeliveryman(healthworker);
        }
    }

    /**
     * A Model stub that always accept the request being added.
     */
    private class ModelStubAcceptingDeliverymanAdded extends ModelStub {
        final ArrayList<Healthworker> deliverymenAdded = new ArrayList<>();

        @Override
        public boolean hasDeliveryman(Healthworker healthworker) {
            requireNonNull(healthworker);
            return deliverymenAdded.stream().anyMatch(healthworker::isSameDeliveryman);
        }

        @Override
        public void addDeliveryman(Healthworker healthworker) {
            requireNonNull(healthworker);
            deliverymenAdded.add(healthworker);
        }

        @Override
        public void commitDeliverymenList() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public HealthworkerList getDeliverymenList() {
            return new HealthworkerList();
        }
    }
}
