package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.order.Request;
import seedu.address.model.order.UniqueRequestList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class OrderBook implements ReadOnlyOrderBook {

    private final UniqueRequestList orders;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        orders = new UniqueRequestList();
    }

    public OrderBook() {
    }

    /**
     * Creates an OrderBook using the Orders in the {@code toBeCopied}
     */
    public OrderBook(ReadOnlyOrderBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the request list with {@code requests}.
     * {@code requests} must not contain duplicate requests.
     */
    public void setOrders(List<Request> requests) {
        this.orders.setOrder(requests);
    }

    /**
     * Resets the existing data of this {@code OrderBook} with {@code newData}.
     */
    public void resetData(ReadOnlyOrderBook newData) {
        requireNonNull(newData);

        setOrders(newData.getOrderList());
    }

    //// common-level operations

    /**
     * Returns true if an request with the same identity as {@code request} exists in the request book.
     */
    public boolean hasOrder(Request person) {
        requireNonNull(person);
        return orders.contains(person);
    }

    /**
     * Adds an request to the request book.
     * The request must not already exist in the request book.
     */
    public void addOrder(Request o) {
        if (o.getTag() == null) {
            o.assignTag();
        }
        orders.add(o);
    }

    /**
     * Replaces the given request {@code target} in the list with {@code editedRequest}.
     * {@code target} must exist in the request book.
     * The request identity of {@code editedRequest} must not be the same as another existing
     * request in the request book.
     */
    public void updateOrder(Request target, Request editedRequest) {
        requireNonNull(editedRequest);

        if (editedRequest.getTag() == null) {
            editedRequest.assignTag();
        }
        orders.setOrder(target, editedRequest);
    }

    /**
     * Removes {@code key} from this {@code OrderBook}.
     * {@code key} must exist in the address book.
     */
    public void removeOrder(Request key) {
        orders.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return orders.asUnmodifiableObservableList().size() + " orders";
        // TODO: refine later
    }

    @Override
    public ObservableList<Request> getOrderList() {
        return orders.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderBook // instanceof handles nulls
                && orders.equals(((OrderBook) other).orders));
    }

    @Override
    public int hashCode() {
        return orders.hashCode();
    }
}
