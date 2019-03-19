package seedu.address.storage;

import static seedu.address.model.TaggedObject.MESSAGE_INVALID_ID;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.deliveryman.Healthworker;
import seedu.address.model.order.Condition;
import seedu.address.model.order.Request;
import seedu.address.model.order.RequestDate;
import seedu.address.model.order.RequestStatus;

/**
 * JAXB-friendly version of the Request.
 */
public class XmlAdaptedRequest {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Request's %s field is missing!";

    @XmlAttribute(required = true)
    @XmlID
    private String tag;

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private List<XmlAdaptedCondition> conditions = new ArrayList<>();
    @XmlElement
    private String deliveryman;

    /**
     * Constructs an XmlAdaptedRequest.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRequest() {
    }

    /**
     * Constructs an {@code XmlAdaptedRequest} with the given request details.
     */
    public XmlAdaptedRequest(String tag, String name, String phone, String address, String date,
                             String status, List<XmlAdaptedCondition> conditions,
                             String deliveryman) {
        this.tag = tag;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.status = status;
        this.deliveryman = deliveryman;

        if (conditions == null) {
            this.conditions = new ArrayList<>();
        } else {
            this.conditions = new ArrayList<>(conditions);
        }
    }

    /**
     * Constructs an {@code XmlAdaptedRequest} with the given request details.
     */
    public XmlAdaptedRequest(String name, String phone, String address, String date, String status,
                             List<XmlAdaptedCondition> conditions, String deliveryman) {
        this(UUID.randomUUID().toString(), name, phone, address, date, status, conditions,
            deliveryman);
    }

    /**
     * Converts a given Request into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRequest
     */
    public XmlAdaptedRequest(Request source) {
        tag = source.getTag().toString();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        date = source.getDate().toString();
        status = source.getRequestStatus().toString();
        conditions = source.getCondition().stream()
            .map(XmlAdaptedCondition::new)
            .collect(Collectors.toList());
        if (source.getHealthworker() != null) {
            deliveryman = source.getHealthworker().getName().fullName;
        }
    }

    /**
     * Converts this jaxb-friendly adapted request object into the model's Request object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               request
     */
    public Request toModelType() throws IllegalValueException {
        final List<Condition> conditionStore = new ArrayList<>();
        for (XmlAdaptedCondition foodItem : conditions) {
            conditionStore.add(foodItem.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);


        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);


        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Date.class.getSimpleName()));
        }
        if (!RequestDate.isValidDate(date)) {
            throw new IllegalValueException(RequestDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final RequestDate modelDate = new RequestDate(date);

        if (conditionStore.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Condition.class.getSimpleName()));
        }
        final Set<Condition> modelCondition = new HashSet<>(conditionStore);

        if (tag == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Tag"));
        }

        UUID modelTag;
        try {
            modelTag = UUID.fromString(tag);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_INVALID_ID);
        }

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                RequestStatus.class.getSimpleName()));
        }
        if (!RequestStatus.isValidStatus(status)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                RequestStatus.class.getSimpleName()));
        }
        final RequestStatus requestStatus = new RequestStatus(status);

        final Healthworker modelHealthworker;
        if (deliveryman == null) {
            modelHealthworker = null;
        } else {
            modelHealthworker = new Healthworker(new Name(deliveryman));
        }

        return new Request(modelTag, modelName, modelPhone, modelAddress, modelDate, requestStatus,
            modelCondition, modelHealthworker);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRequest)) {
            return false;
        }

        XmlAdaptedRequest otherOrder = (XmlAdaptedRequest) other;
        return tag.equals(otherOrder.tag)
            && Objects.equals(name, otherOrder.name)
            && Objects.equals(phone, otherOrder.phone)
            && Objects.equals(address, otherOrder.address)
            && Objects.equals(date, otherOrder.date)
            && Objects.equals(status, otherOrder.status)
            && conditions.equals(otherOrder.conditions)
            && Objects.equals(deliveryman, otherOrder.deliveryman);
    }

    @Override
    public String toString() {
        return "Tag: " + this.tag + "\n"
            + "Name: " + this.name + "\n"
            + "Phone: " + this.phone + "\n"
            + "Address: " + this.address + "\n"
            + "Date: " + this.date + "\n"
            + "Status: " + this.status + "\n"
            + "Conditions: " + this.conditions + "\n"
            + "Healthworker: " + this.deliveryman;
    }
}
