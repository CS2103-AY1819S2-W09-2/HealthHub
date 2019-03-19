package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.request.Request;
import seedu.address.model.request.UniqueRequestList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class RequestBook implements ReadOnlyRequestBook {

    private final UniqueRequestList requests;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        requests = new UniqueRequestList();
    }

    public RequestBook() {
    }

    /**
     * Creates an RequestBook using the Orders in the {@code toBeCopied}
     */
    public RequestBook(ReadOnlyRequestBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the request list with {@code requests}.
     * {@code requests} must not contain duplicate requests.
     */
    public void setRequests(List<Request> requests) {
        this.requests.setRequest(requests);
    }

    /**
     * Resets the existing data of this {@code RequestBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRequestBook newData) {
        requireNonNull(newData);

        setRequests(newData.getRequestList());
    }

    //// common-level operations

    /**
     * Returns true if an request with the same identity as {@code request} exists in the request book.
     */
    public boolean hasRequest(Request request) {
        requireNonNull(request);
        return requests.contains(request);
    }

    /**
     * Adds an request to the request book.
     * The request must not already exist in the request book.
     */
    public void addRequest(Request request) {
        if (request.getTag() == null) {
            request.assignTag();
        }
        requests.add(request);
    }

    /**
     * Replaces the given request {@code target} in the list with {@code editedRequest}.
     * {@code target} must exist in the request book.
     * The request identity of {@code editedRequest} must not be the same as another existing
     * request in the request book.
     */
    public void updateRequest(Request target, Request editedRequest) {
        requireNonNull(editedRequest);

        if (editedRequest.getTag() == null) {
            editedRequest.assignTag();
        }
        requests.setRequest(target, editedRequest);
    }

    /**
     * Removes {@code key} from this {@code RequestBook}.
     * {@code key} must exist in the address book.
     */
    public void removeRequest(Request key) {
        requests.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return requests.asUnmodifiableObservableList().toString();
        // TODO: refine later
    }

    @Override
    public ObservableList<Request> getRequestList() {
        return requests.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RequestBook // instanceof handles nulls
            && requests.equals(((RequestBook) other).requests));
    }

    @Override
    public int hashCode() {
        return requests.hashCode();
    }
}
