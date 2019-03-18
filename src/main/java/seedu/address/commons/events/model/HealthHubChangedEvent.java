package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.deliveryman.DeliverymenList;

/**
 * Indicates that some part of the app has changed.
 */
public class HealthHubChangedEvent extends BaseEvent {

    public final ReadOnlyOrderBook orderBook;

    public final DeliverymenList deliverymenList;

    public HealthHubChangedEvent(ReadOnlyOrderBook orderBook, DeliverymenList deliverymenList) {
        this.orderBook = orderBook;
        this.deliverymenList = deliverymenList;
    }

    @Override
    public String toString() {
        return "number of orders " + orderBook.getOrderList().size() + deliverymenList.getDeliverymenList().size();
    }
}
