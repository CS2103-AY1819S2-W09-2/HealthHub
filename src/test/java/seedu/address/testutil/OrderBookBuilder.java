package seedu.address.testutil;

import seedu.address.model.OrderBook;
import seedu.address.model.order.Request;

/**
 * A utility class to help with building Orderbook objects.
 * Example usage: <br>
 * {@code OrderBook ab = new OrderBookBuilder().withPerson("John", "Doe").build();}
 */
public class OrderBookBuilder {

    private OrderBook orderBook;

    public OrderBookBuilder() {
        orderBook = new OrderBook();
    }

    public OrderBookBuilder(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    /**
     * Adds a new {@code Request} to the {@code OrderBook} that we are building.
     */
    public OrderBookBuilder withOrder(Request request) {
        orderBook.addOrder(request);
        return this;
    }

    public OrderBook build() {
        return orderBook;
    }
}
