package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.deliveryman.Healthworker;
import seedu.address.model.deliveryman.HealthworkerList;
import seedu.address.model.order.Request;
import seedu.address.model.user.User;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Request> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<User> PREDICATE_SHOW_ALL_USERS = unused -> true;
    /**
     * {@code Predicate} that always evaluates to true
     */
    Predicate<Healthworker> PREDICATE_SHOW_ALL_DELIVERYMEN = unused -> true;

    // ==================== request book/request related methods =======================

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyOrderBook newData);

    /**
     * Returns the OrderBook
     */
    ReadOnlyOrderBook getOrderBook();

    /**
     * Returns true if a common with the same identity as {@code request} exists in the request book.
     */
    boolean hasOrder(Request request);

    /**
     * Deletes the given request.
     * The request must exist in the address book.
     */
    void deleteOrder(Request target);

    /**
     * Adds the given request.
     * {@code request} must not already exist in the address book.
     */
    void addOrder(Request request);

    /**
     * Replaces the given request {@code target} with {@code editedRequest}.
     * {@code target} must exist in the address book.
     * The request identity of {@code editedRequest} must not be the same as another existing
     * request in the request book.
     */
    void updateOrder(Request target, Request editedRequest);

    /**
     * Returns an unmodifiable view of the filtered request list
     */
    ObservableList<Request> getFilteredOrderList();

    /**
     * Updates the filter of the filtered request list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Request> predicate);

    /**
     * Returns true if the model has previous request book states to restore.
     */
    boolean canUndoOrderBook();

    /**
     * Returns true if the model has undone request book states to restore.
     */
    boolean canRedoOrderBook();

    /**
     * Restores the model's request book to its previous state.
     */
    void undoOrderBook();

    /**
     * Restores the model's request book to its previously undone state.
     */
    void redoOrderBook();

    /**
     * Saves the current request book state for undo/redo.
     */
    void commitOrderBook();

    // ======================== User related methods =========================

    /**
     * Return true if user exist inside user list.
     */
    boolean hasUser(User user);

    /**
     * Add user to usersList.
     */
    void addUser(User user);

    /**
     * Saves the current users list for undo/redo.
     */
    void commitUsersList();

    /**
     * Updates the filter of the filtered user list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredUsersList(Predicate<User> predicate);


    /**
     * Returns an unmodifiable view of the filtered users list
     */
    ObservableList<User> getFilteredUsersList();

    /**
     * Return true if user is registered with application.
     */
    boolean isRegisteredUser(User user);

    /**
     * Returns the UsersList
     */
    ReadOnlyUsersList getUsersList();


    /**
     * Return true if user is logged into FoodZoom.
     */
    boolean isUserLoggedIn();

    /**
     * Store the user session details.
     */
    void storeUserInSession(User user);

    /**
     * Return the logged in user details.
     */
    User getLoggedInUserDetails();

    /**
     * Clear the user session details.
     */
    void clearUserInSession();

    // ======================== Healthworker related methods =========================

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetDeliverymenData(HealthworkerList newData);

    /**
     * Returns the HealthworkerList
     */
    HealthworkerList getDeliverymenList();

    /**
     * Returns true if a healthworker with the same identity as {@code healthworker} exists in the address book.
     */
    boolean hasDeliveryman(Healthworker healthworker);

    /**
     * Deletes the given healthworker.
     * The healthworker must exist in the address book.
     */
    void deleteDeliveryman(Healthworker target);

    /**
     * Adds the given healthworker.
     * {@code healthworker} must not already exist in the address book.
     */
    void addDeliveryman(Healthworker healthworker);

    /**
     * Replaces the given healthworker {@code target} with {@code editedHealthworker}.
     * {@code target} must exist in the address book.
     * The healthworker identity of {@code editedHealthworker} must not be the same as another
     * existing healthworker in the deliverymen list.
     */
    void updateDeliveryman(Healthworker target, Healthworker editedHealthworker);

    /**
     * Returns an unmodifiable view of the filtered healthworker list
     */
    ObservableList<Healthworker> getFilteredDeliverymenList();

    /**
     * Updates the filter of the filtered healthworker list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeliverymenList(Predicate<Healthworker> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoDeliverymenList();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoDeliverymenList();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoDeliverymenList();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoDeliverymenList();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitDeliverymenList();
}
