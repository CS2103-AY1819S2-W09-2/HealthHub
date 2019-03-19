package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.order.Request;

/**
 * Unmodifiable view of an request book
 */
public interface ReadOnlyRequestBook {

    /**
     * Returns an unmodifiable view of the orders list.
     * This list will not contain any duplicate orders.
     */
    ObservableList<Request> getRequestList();

}
