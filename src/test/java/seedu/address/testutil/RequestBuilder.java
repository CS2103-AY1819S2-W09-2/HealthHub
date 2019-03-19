package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.healthworker.Healthworker;
import seedu.address.model.order.Condition;
import seedu.address.model.order.Request;
import seedu.address.model.order.RequestDate;
import seedu.address.model.order.RequestStatus;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Request objects.
 */
public class RequestBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111, 612321";
    public static final String DEFAULT_STATUS = "PENDING";
    public static final String DEFAULT_DATE = "01-10-2018 10:00:00";
    public static final String DEFAULT_FOOD = "Fried Rice";

    private Name name;
    private Phone phone;
    private Address address;
    private RequestDate date;
    private RequestStatus status;
    private Set<Condition> condition;
    private UUID id;
    private Healthworker healthworker;

    public RequestBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        date = new RequestDate(DEFAULT_DATE);
        condition = SampleDataUtil.getConditionSet(DEFAULT_FOOD);
        status = new RequestStatus(DEFAULT_STATUS);
        healthworker = null;
    }

    /**
     * Initializes the RequestBuilder with the data of {@code requestToCopy}.
     */
    public RequestBuilder(Request requestToCopy) {
        id = requestToCopy.getTag();
        name = requestToCopy.getName();
        phone = requestToCopy.getPhone();
        address = requestToCopy.getAddress();
        date = requestToCopy.getDate();
        status = requestToCopy.getRequestStatus();
        condition = new HashSet<>(requestToCopy.getCondition());
        healthworker = requestToCopy.getHealthworker();
    }

    /**
     * Sets the {@code id} of the {@code Request} that we are building
     *
     * @param id
     */
    public RequestBuilder withId(String id) {
        this.id = UUID.fromString(id);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Request} that we are building.
     */
    public RequestBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code condition} into a {@code Set<Condition>} and set it to the {@code Request} that we are building.
     */
    public RequestBuilder withCondition(String... conditions) {
        this.condition = SampleDataUtil.getConditionSet(conditions);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public RequestBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Request} that we are building.
     */
    public RequestBuilder withDate(String orderDate) {
        this.date = new RequestDate(orderDate);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Request} that we are building.
     */
    public RequestBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Request} that we are building.
     */
    public RequestBuilder withStatus(String status) {
        this.status = new RequestStatus(status);
        return this;
    }

    /**
     * Sets the {@code Healthworker} of the {@code Request} that we are building.
     */
    public RequestBuilder withDeliveryman(Healthworker healthworker) {
        this.healthworker = healthworker;
        return this;
    }

    /**
     * Builds and returns an request.
     */
    public Request build() {
        if (id != null) {
            return new Request(id, name, phone, address, date, status, condition, healthworker);
        } else {
            return new Request(name, phone, address, date, status, condition);
        }
    }

}
