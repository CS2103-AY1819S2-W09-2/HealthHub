package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.HealthHubChangedEvent;
import seedu.address.commons.events.model.UserLoggedInEvent;
import seedu.address.commons.events.model.UserLoggedOutEvent;
import seedu.address.commons.events.model.UsersListChangedEvent;
import seedu.address.commons.events.ui.BackToHomeEvent;
import seedu.address.model.healthworker.Healthworker;
import seedu.address.model.healthworker.HealthworkerList;
import seedu.address.model.healthworker.VersionedHealthworkerList;
import seedu.address.model.request.Request;
import seedu.address.model.user.User;
import seedu.address.model.user.UserSession;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedRequestBook versionedOrderBook;
    private final VersionedUsersList versionedUsersList;
    private final FilteredList<Request> filteredRequests;
    private final FilteredList<User> filteredUsers;

    private final VersionedHealthworkerList versionedHealthworkerList;
    private final FilteredList<Healthworker> filteredDeliverymen;

    private final UserSession userSession;

    /**
     * Initializes a ModelManager with the given addressBook, usersList and userPrefs.
     */
    public ModelManager(ReadOnlyRequestBook orderBook, ReadOnlyUsersList usersList,
                        HealthworkerList healthworkerList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(orderBook, userPrefs, healthworkerList);

        logger.fine("Initializing with request book: " + orderBook
            + " and users list " + usersList
            + " and deliverymen list " + healthworkerList
            + " and user prefs " + userPrefs);

        versionedOrderBook = new VersionedRequestBook(orderBook);
        versionedUsersList = new VersionedUsersList(usersList);
        versionedHealthworkerList = new VersionedHealthworkerList(healthworkerList);
        filteredRequests = new FilteredList<>(versionedOrderBook.getRequestList());
        filteredUsers = new FilteredList<>(versionedUsersList.getUserList());
        filteredDeliverymen = new FilteredList<>(versionedHealthworkerList.getDeliverymenList());

        userSession = new UserSession();

        logger.fine("Initializing with request book: " + orderBook
            + " and users list "
            + usersList
            + " and deliverymen list"
            + healthworkerList
            + " and user prefs "
            + userPrefs);
    }

    public ModelManager() {
        this(new RequestBook(), new UsersList(), new HealthworkerList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyRequestBook newData) {
        versionedOrderBook.resetData(newData);
        indicateAppChanged();
    }

    @Override
    public void resetDeliverymenData(HealthworkerList newData) {
        versionedHealthworkerList.resetData(newData);
        indicateAppChanged();
    }

    @Override
    public ReadOnlyRequestBook getOrderBook() {
        return versionedOrderBook;
    }

    @Override
    public HealthworkerList getDeliverymenList() {
        return versionedHealthworkerList;
    }

    /**
     * Raises an event to indicate that there is an app change.
     */
    private void indicateAppChanged() {
        raise(new HealthHubChangedEvent(versionedOrderBook, versionedHealthworkerList));
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateUsersListChanged() {
        raise(new UsersListChangedEvent(versionedUsersList));
    }

    /**
     * Raises an event to indicate user have logged in.
     */
    private void indicateUserLoggedIn(User user) {
        raise(new UserLoggedInEvent(user));
    }

    /**
     * Raises an event to indicate user have logged out.
     */
    private void indicateUserLoggedOut() {
        raise(new UserLoggedOutEvent());
    }

    @Override
    public boolean hasOrder(Request request) {
        requireNonNull(request);
        return versionedOrderBook.hasRequest(request);
    }

    @Override
    public void deleteOrder(Request target) {
        versionedOrderBook.removeRequest(target);
        indicateAppChanged();
    }

    @Override
    public void addOrder(Request request) {
        versionedOrderBook.addRequest(request);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        indicateAppChanged();
    }

    @Override
    public void updateOrder(Request target, Request editedRequest) {
        requireAllNonNull(target, editedRequest);

        versionedOrderBook.updateRequest(target, editedRequest);
        indicateAppChanged();
    }

    // =========== Healthworker methods ====================================

    @Override
    public boolean hasDeliveryman(Healthworker healthworker) {
        requireNonNull(healthworker);
        return versionedHealthworkerList.hasDeliveryman(healthworker);
    }

    @Override
    public void deleteDeliveryman(Healthworker target) {
        versionedHealthworkerList.removeDeliveryman(target);
        indicateAppChanged();
    }

    @Override
    public void addDeliveryman(Healthworker healthworker) {
        versionedHealthworkerList.addDeliveryman(healthworker);
        indicateAppChanged();
    }

    @Override
    public void updateDeliveryman(Healthworker target, Healthworker editedHealthworker) {
        requireAllNonNull(target, editedHealthworker);

        versionedHealthworkerList.updateDeliveryman(target, editedHealthworker);
        indicateAppChanged();
    }

    //=========== Filtered Orders List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Request} backed by the internal list of
     * {@code versionedOrderBook}
     */
    @Override
    public ObservableList<Request> getFilteredOrderList() {
        return FXCollections.unmodifiableObservableList(filteredRequests);
    }

    @Override
    public void updateFilteredOrderList(Predicate<Request> predicate) {
        requireNonNull(predicate);
        filteredRequests.setPredicate(predicate);
        EventsCenter.getInstance().post(new BackToHomeEvent());
    }

    //=========== Filtered Healthworker List Accessors =======================================================

    /**
     * Returns an unmodifiable view of the list of {@code Healthworker} backed by the internal list
     * of {@code versionedHealthworkerList}
     */
    @Override
    public ObservableList<Healthworker> getFilteredDeliverymenList() {
        return FXCollections.unmodifiableObservableList(filteredDeliverymen);
    }

    @Override
    public void updateFilteredDeliverymenList(Predicate<Healthworker> predicate) {
        requireNonNull(predicate);
        filteredDeliverymen.setPredicate(predicate);
        EventsCenter.getInstance().post(new BackToHomeEvent());
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoOrderBook() {
        return versionedOrderBook.canUndo();
    }

    @Override
    public boolean canRedoOrderBook() {
        return versionedOrderBook.canRedo();
    }

    @Override
    public void undoOrderBook() {
        versionedOrderBook.undo();
        indicateAppChanged();
    }

    @Override
    public void redoOrderBook() {
        versionedOrderBook.redo();
        indicateAppChanged();
    }

    @Override
    public void commitOrderBook() {
        versionedOrderBook.commit();
    }


    //=========== Filtered User List Accessors =============================================================
    @Override
    public boolean hasUser(User user) {
        requireNonNull(user);
        return versionedUsersList.hasUser(user);
    }

    @Override
    public void addUser(User user) {
        versionedUsersList.addUser(user);
        updateFilteredUsersList(PREDICATE_SHOW_ALL_USERS);
        indicateUsersListChanged();
    }

    @Override
    public void commitUsersList() {
        versionedUsersList.commit();
    }

    @Override
    public void updateFilteredUsersList(Predicate<User> predicate) {
        requireNonNull(predicate);
        filteredUsers.setPredicate(predicate);
    }

    @Override
    public ObservableList<User> getFilteredUsersList() {
        return FXCollections.unmodifiableObservableList(filteredUsers);
    }

    @Override
    public boolean isRegisteredUser(User user) {
        requireNonNull(user);
        return versionedUsersList.isRegisteredUser(user);
    }

    @Override
    public ReadOnlyUsersList getUsersList() {
        return versionedUsersList;
    }

    @Override
    public boolean isUserLoggedIn() {
        return userSession.isUserAlreadyLoggedIn();
    }

    @Override
    public void storeUserInSession(User user) {
        userSession.setUserSession(user);
        indicateUserLoggedIn(user);
    }

    @Override
    public User getLoggedInUserDetails() {
        return userSession.getLoggedInUserDetails();
    }

    @Override
    public void clearUserInSession() {
        userSession.clearUserSession();
        indicateUserLoggedOut();
    }

    @Override
    public boolean canUndoDeliverymenList() {
        return versionedHealthworkerList.canUndo();
    }

    @Override
    public boolean canRedoDeliverymenList() {
        return versionedHealthworkerList.canRedo();
    }

    @Override
    public void undoDeliverymenList() {
        versionedHealthworkerList.undo();
        indicateAppChanged();
    }

    @Override
    public void redoDeliverymenList() {
        versionedHealthworkerList.redo();
        indicateAppChanged();
    }

    @Override
    public void commitDeliverymenList() {
        versionedHealthworkerList.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedOrderBook.equals(other.versionedOrderBook)
            && filteredRequests.equals(other.filteredRequests)
            && versionedUsersList.equals(other.versionedUsersList)
            && filteredUsers.equals(other.filteredUsers)
            && versionedHealthworkerList.equals(other.versionedHealthworkerList)
            && filteredDeliverymen.equals(other.filteredDeliverymen);
    }


}
