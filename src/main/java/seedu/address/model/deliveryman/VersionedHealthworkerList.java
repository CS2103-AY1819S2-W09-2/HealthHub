package seedu.address.model.deliveryman;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a versioned version of the deliverymen list.
 */
public class VersionedHealthworkerList extends HealthworkerList {

    private final List<HealthworkerList> healthworkerListStateList;
    private int currentStatePointer;

    public VersionedHealthworkerList(HealthworkerList initialState) {
        super(initialState);

        healthworkerListStateList = new ArrayList<>();
        healthworkerListStateList.add(new HealthworkerList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code HealthworkerList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        healthworkerListStateList.add(new HealthworkerList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        healthworkerListStateList.subList(currentStatePointer + 1, healthworkerListStateList.size()).clear();
    }

    /**
     * Restores the deliverymen list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(healthworkerListStateList.get(currentStatePointer));
    }

    /**
     * Restores the deliverymen list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(healthworkerListStateList.get(currentStatePointer));
    }

    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    public boolean canRedo() {
        return currentStatePointer < healthworkerListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VersionedHealthworkerList)) {
            return false;
        }

        VersionedHealthworkerList otherList = (VersionedHealthworkerList) other;

        return super.equals(otherList)
                && healthworkerListStateList.equals(otherList.healthworkerListStateList)
                && currentStatePointer == otherList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of deliverymenListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
