package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code RequestBook} that keeps track of its own history.
 */
public class VersionedRequestBook extends RequestBook {

    private final List<ReadOnlyRequestBook> orderBookStateList;
    private int currentStatePointer;

    public VersionedRequestBook(ReadOnlyRequestBook initialState) {
        super(initialState);

        orderBookStateList = new ArrayList<>();
        orderBookStateList.add(new RequestBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code RequestBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        orderBookStateList.add(new RequestBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        orderBookStateList.subList(currentStatePointer + 1, orderBookStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(orderBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(orderBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < orderBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedRequestBook)) {
            return false;
        }

        VersionedRequestBook otherVersionedOrderBook = (VersionedRequestBook) other;

        // state check
        return super.equals(otherVersionedOrderBook)
            && orderBookStateList.equals(otherVersionedOrderBook.orderBookStateList)
            && currentStatePointer == otherVersionedOrderBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of orderBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of orderBookState list, unable to redo.");
        }
    }
}
