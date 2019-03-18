package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.order.Order;

/**
 * Represents a selection change in the Person List Panel
 */
public class RequestPanelSelectionChangedEvent extends BaseEvent {


    private final Order newSelection;

    public RequestPanelSelectionChangedEvent(Order newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Order getNewSelection() {
        return newSelection;
    }
}
