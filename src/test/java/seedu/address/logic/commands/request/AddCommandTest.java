package seedu.address.logic.commands.request;

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
import seedu.address.model.RequestBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.deliveryman.Healthworker;
import seedu.address.model.deliveryman.HealthworkerList;
import seedu.address.model.order.Request;
import seedu.address.model.user.User;
import seedu.address.testutil.RequestBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        Request validRequest = new RequestBuilder().build();

        CommandResult commandResult = new AddCommand(validRequest).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validRequest), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRequest), modelStub.ordersAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() throws Exception {
        Request validRequest = new RequestBuilder().build();
        AddCommand addCommand = new AddCommand(validRequest);
        ModelStub modelStub = new ModelStubWithOrder(validRequest);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_ORDER);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Request alice = new RequestBuilder().withName("Alice").build();
        Request bob = new RequestBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different common -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addOrder(Request request) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeliveryman(Healthworker healthworker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyRequestBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetDeliverymenData(HealthworkerList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRequestBook getOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HealthworkerList getDeliverymenList() {
            throw new AssertionError(" This message should not be called");
        }

        @Override
        public boolean hasOrder(Request request) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOrder(Request target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateOrder(Request target, Request editedRequest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Request> getFilteredOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderList(Predicate<Request> predicate) {
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
    private class ModelStubWithOrder extends ModelStub {
        private final Request request;

        ModelStubWithOrder(Request request) {
            requireNonNull(request);
            this.request = request;
        }

        @Override
        public boolean hasOrder(Request request) {
            requireNonNull(request);
            return this.request.isSameOrder(request);
        }
    }

    /**
     * A Model stub that always accept the request being added.
     */
    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final ArrayList<Request> ordersAdded = new ArrayList<>();

        @Override
        public boolean hasOrder(Request person) {
            requireNonNull(person);
            return ordersAdded.stream().anyMatch(person::isSameOrder);
        }

        @Override
        public void addOrder(Request request) {
            requireNonNull(request);
            ordersAdded.add(request);
        }

        @Override
        public void commitOrderBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyRequestBook getOrderBook() {
            return new RequestBook();
        }
    }

}
