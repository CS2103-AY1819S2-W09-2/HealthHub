package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.exceptions.DuplicateRequestException;
import seedu.address.model.order.exceptions.RequestNotFoundException;

/**
 * A list of orders that enforces uniqueness between its elements and does not allow nulls.
 * An request is considered unique by comparing using {@code Order#isSameOrder(Order)}. As such, adding and updating of
 * orders uses Order#isSameOrder(Order) for equality so as to ensure that the Order being added or updated is
 * unique in terms of identity in the UniqueOrderList. However, the removal of an Order uses Order#equals(Object) so
 * as to ensure that the request with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Order#isSameOrder(Order)
 */
public class UniqueOrderList implements Iterable<Order> {

    private final ObservableList<Order> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent request as the given argument.
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameOrder);
    }

    /**
     * Adds an Order to the list.
     * The request must not already exist in the list.
     */
    public void add(Order toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRequestException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the request {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the list.
     * The request identity of {@code editedOrder} must not be the same as another existing request in the list.
     */
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RequestNotFoundException();
        }

        if (!target.isSameOrder(editedOrder) && contains(editedOrder)) {
            throw new DuplicateRequestException();
        }

        internalList.set(index, editedOrder);
    }

    public void setOrder(UniqueOrderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code orders}.
     * {@code orders} must not contain duplicate persons.
     */
    public void setOrder(List<Order> orders) {
        requireAllNonNull(orders);
        if (!ordersAreUnique(orders)) {
            throw new DuplicateRequestException();
        }

        internalList.setAll(orders);
    }

    /**
     * Removes the equivalent request from the list.
     * The request must exist in the list.
     */
    public void remove(Order toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RequestNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Order> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueOrderList // instanceof handles nulls
                && internalList.equals(((UniqueOrderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code orders} contains only unique orders.
     */
    private boolean ordersAreUnique(List<Order> orders) {
        for (int i = 0; i < orders.size() - 1; i++) {
            for (int j = i + 1; j < orders.size(); j++) {
                if (orders.get(i).isSameOrder(orders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
