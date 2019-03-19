package seedu.address.storage.deliveryman;

import static seedu.address.model.TaggedObject.MESSAGE_INVALID_ID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Name;
import seedu.address.model.deliveryman.Healthworker;
import seedu.address.model.order.Request;
import seedu.address.storage.XmlAdaptedRequest;

/**
 * Represents the XML for storage of Healthworker
 */
public class XmlAdaptedDeliveryman {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Healthworker's %s field is missing!";

    @XmlAttribute
    @XmlID
    private String tag;

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    @XmlIDREF
    private List<XmlAdaptedRequest> orders = new ArrayList<>();

    public XmlAdaptedDeliveryman() {
    }

    /**
     * Constructs an {@code XmlAdapterDeliveryman} with the given common details.
     */
    public XmlAdaptedDeliveryman(String tag, String name, List<XmlAdaptedRequest> orders) {
        this.tag = tag;
        this.name = name;
        if (orders == null) {
            this.orders = new ArrayList<>();
        } else {
            this.orders = new ArrayList<>(orders);
        }
    }

    /**
     * Constructs an {@code XmlAdapterDeliveryman} with the given common details.
     */
    public XmlAdaptedDeliveryman(String name) {
        this.tag = UUID.randomUUID().toString();
        this.name = name;
    }

    /**
     * Converts a given healthworker into this class for JAXB use.
     *
     * @param source
     */
    public XmlAdaptedDeliveryman(Healthworker source) {
        tag = source.getTag().toString();
        name = source.getName().fullName;
        orders = source.getRequests().stream()
            .map(XmlAdaptedRequest::new)
            .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted healthworker object into the model's Healthworker object.
     *
     * @throws IllegalValueException If there were any data constraints violated.
     */
    public Healthworker toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }

        if (tag == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Tag"));
        }

        UUID modelTag;

        try {
            modelTag = UUID.fromString(tag);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_INVALID_ID);
        }

        final Name modelName = new Name(name);

        final List<Request> requestStore = new ArrayList<>();
        for (XmlAdaptedRequest orderItem : orders) {
            requestStore.add(orderItem.toModelType());
        }
        final Set<Request> modelRequest = new HashSet<>(requestStore);

        return new Healthworker(modelTag, modelName, modelRequest);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDeliveryman)) {
            return false;
        }

        XmlAdaptedDeliveryman otherDman = (XmlAdaptedDeliveryman) other;
        return tag.equals(otherDman.tag)
            && Objects.equals(name, otherDman.name);
    }
}
