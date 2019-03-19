package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.RequestBook;
import seedu.address.model.healthworker.HealthworkerList;
import seedu.address.storage.healthworker.XmlSerializableHealthworkerList;

/**
 * JAXB storage of FoodZoom information - namely an orderBook and a healthworkerList.
 */
@XmlRootElement(name = "healthhub")
public class XmlHealthHub {
    @XmlElement(required = true)
    private XmlSerializableRequestBook orderBook;

    @XmlElement(required = true)
    private XmlSerializableHealthworkerList deliverymenList;

    public XmlHealthHub() {
        orderBook = new XmlSerializableRequestBook();
        deliverymenList = new XmlSerializableHealthworkerList();
    }

    public XmlHealthHub(ReadOnlyRequestBook ordersSrc, HealthworkerList deliverymenSrc) {
        orderBook = new XmlSerializableRequestBook(ordersSrc);
        deliverymenList = new XmlSerializableHealthworkerList(deliverymenSrc);
    }

    public RequestBook getOrderBook() throws IllegalValueException {
        return orderBook.toModelType();
    }

    public HealthworkerList getDeliverymenList() throws IllegalValueException {
        return deliverymenList.toModelType();
    }
}
