package seedu.address.storage.deliveryman;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deliveryman.Healthworker;
import seedu.address.model.deliveryman.HealthworkerList;

/**
 * An Immutable HealthworkerList that is serializable to XML format
 */
public class XmlSerializableHealthworkerList {

    public static final String MESSAGE_DUPLICATE_DELIVERYMAN = "Deliverymen List contains duplicate deliverymen.";

    @XmlElement
    private List<XmlAdaptedDeliveryman> deliverymen;

    /**
     * Creates an empty XmlSerializableHealthworkerList.
     * Required for marshalling.
     */
    public XmlSerializableHealthworkerList() {
        deliverymen = new ArrayList<>();
    }

    /**
     * Converts HealthworkerList to Serializable
     */
    public XmlSerializableHealthworkerList(HealthworkerList src) {
        this();
        deliverymen.addAll(src.getDeliverymenList().stream().map(XmlAdaptedDeliveryman::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this deliverymen list into the model's {@code HealthworkerList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or dupicates
     */
    public HealthworkerList toModelType() throws IllegalValueException {
        HealthworkerList healthworkerList = new HealthworkerList();
        for (XmlAdaptedDeliveryman d : deliverymen) {
            Healthworker dMan = d.toModelType();
            if (healthworkerList.hasDeliveryman(dMan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DELIVERYMAN);
            }
            healthworkerList.addDeliveryman(dMan);
        }
        return healthworkerList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableHealthworkerList)) {
            return false;
        }
        return deliverymen.equals(((XmlSerializableHealthworkerList) other).deliverymen);
    }
}
