package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.healthworker.Healthworker;

/**
 * Represents a selection change in the Person List Panel
 */
public class HealthWorkerPanelSelectionChangedEvent extends BaseEvent {


    private final Healthworker newSelection;

    public HealthWorkerPanelSelectionChangedEvent(Healthworker newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Healthworker getNewSelection() {
        return newSelection;
    }
}
