package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.deliveryman.HealthworkerList;

/**
 * Indicates that some part of the app has changed.
 */
public class HealthHubChangedEvent extends BaseEvent {

    public final ReadOnlyOrderBook orderBook;

    public final HealthworkerList healthworkerList;

    public HealthHubChangedEvent(ReadOnlyOrderBook orderBook, HealthworkerList healthworkerList) {
        this.orderBook = orderBook;
        this.healthworkerList = healthworkerList;
    }

    @Override
    public String toString() {
        return "number of orders " + orderBook.getOrderList().size() + healthworkerList.getDeliverymenList().size();
    }
}
