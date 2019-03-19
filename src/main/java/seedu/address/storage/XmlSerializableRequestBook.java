package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.RequestBook;
import seedu.address.model.request.Request;

/**
 * An Immutable RequestBook that is serializable to XML format
 */
public class XmlSerializableRequestBook {

    public static final String MESSAGE_DUPLICATE_ORDER = "Orders list contains duplicate request(s).";

    @XmlElement
    private List<XmlAdaptedRequest> orders;

    /**
     * Creates an empty XmlSerializableRequestBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableRequestBook() {
        orders = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableRequestBook(ReadOnlyRequestBook src) {
        this();
        orders.addAll(src.getRequestList().stream().map(XmlAdaptedRequest::new).collect(Collectors.toList()));
    }

    /**
     * Converts this orderbook into the model's {@code RequestBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedRequest}.
     */
    public RequestBook toModelType() throws IllegalValueException {
        RequestBook requestBook = new RequestBook();
        for (XmlAdaptedRequest o : orders) {
            Request request = o.toModelType();
            if (requestBook.hasRequest(request)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ORDER);
            }
            requestBook.addRequest(request);
        }
        return requestBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableRequestBook)) {
            return false;
        }
        return orders.equals(((XmlSerializableRequestBook) other).orders);
    }

    @Override
    public String toString() {
        return this.orders.toString();
    }
}
