package seedu.address.testutil;

import seedu.address.model.RequestBook;
import seedu.address.model.order.Request;

/**
 * A utility class to help with building Orderbook objects.
 * Example usage: <br>
 * {@code RequestBook ab = new OrderBookBuilder().withPerson("John", "Doe").build();}
 */
public class OrderBookBuilder {

    private RequestBook orderBook;

    public OrderBookBuilder() {
        orderBook = new RequestBook();
    }

    public OrderBookBuilder(RequestBook orderBook) {
        this.orderBook = orderBook;
    }

    /**
     * Adds a new {@code Request} to the {@code RequestBook} that we are building.
     */
    public OrderBookBuilder withOrder(Request request) {
        orderBook.addRequest(request);
        return this;
    }

    public RequestBook build() {
        return orderBook;
    }
}
