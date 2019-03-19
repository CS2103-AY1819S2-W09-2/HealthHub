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
 * An request is considered unique by comparing using {@code Request#isSameOrder(Request)}. As such,
 * adding and updating of orders uses Request#isSameOrder(Request) for equality so as to ensure
 * that the Request being added or updated is unique in terms of identity in the UniqueRequestList.
 * However, the removal of an Request uses Request#equals(Object) so as to ensure that the
 * request with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Request#isSameOrder(Request)
 */
public class UniqueRequestList implements Iterable<Request> {

    private final ObservableList<Request> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent request as the given argument.
     */
    public boolean contains(Request toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameOrder);
    }

    /**
     * Adds an Request to the list.
     * The request must not already exist in the list.
     */
    public void add(Request toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRequestException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the request {@code target} in the list with {@code editedRequest}.
     * {@code target} must exist in the list.
     * The request identity of {@code editedRequest} must not be the same as another existing request in the list.
     */
    public void setRequest(Request target, Request editedRequest) {
        requireAllNonNull(target, editedRequest);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RequestNotFoundException();
        }

        if (!target.isSameOrder(editedRequest) && contains(editedRequest)) {
            throw new DuplicateRequestException();
        }

        internalList.set(index, editedRequest);
    }

    public void setRequest(UniqueRequestList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code requests}.
     * {@code requests} must not contain duplicate persons.
     */
    public void setRequest(List<Request> requests) {
        requireAllNonNull(requests);
        if (!ordersAreUnique(requests)) {
            throw new DuplicateRequestException();
        }

        internalList.setAll(requests);
    }

    /**
     * Removes the equivalent request from the list.
     * The request must exist in the list.
     */
    public void remove(Request toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RequestNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Request> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Request> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueRequestList // instanceof handles nulls
            && internalList.equals(((UniqueRequestList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code requests} contains only unique requests.
     */
    private boolean ordersAreUnique(List<Request> requests) {
        for (int i = 0; i < requests.size() - 1; i++) {
            for (int j = i + 1; j < requests.size(); j++) {
                if (requests.get(i).isSameOrder(requests.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
