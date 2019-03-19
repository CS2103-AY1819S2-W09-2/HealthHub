package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.TaggedObject;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.deliveryman.Healthworker;
import seedu.address.model.deliveryman.exceptions.RequestLimitExceededException;

/**
 * Represents an Request in the request book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Request extends TaggedObject {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Address address;
    private final OrderDate orderDate;
    private final Set<Food> food = new HashSet<>();
    private OrderStatus orderStatus;
    private Healthworker healthworker;

    /**
     * Every field must be present and not null.
     */
    public Request(Name name, Phone phone, Address address, OrderDate orderDate,
                   Set<Food> food) {
        this(null, name, phone, address, orderDate, new OrderStatus(), food, null);
    }

    /**
     * Every field must be present and not null.
     */
    public Request(Name name, Phone phone, Address address, OrderDate orderDate, OrderStatus orderStatus,
                   Set<Food> food) {
        this(null, name, phone, address, orderDate, orderStatus, food, null);
    }

    /**
     * Every field must be present and not null besides healthworker.
     */
    public Request(Name name, Phone phone, Address address, OrderDate orderDate, OrderStatus orderStatus,
                   Set<Food> food, Healthworker healthworker) {
        this(null, name, phone, address, orderDate, orderStatus, food, healthworker);
    }

    /**
     * This constructor is used to create an {@code request} with a specified id.
     */
    public Request(UUID id, Name name, Phone phone, Address address, OrderDate orderDate, OrderStatus orderStatus,
                   Set<Food> food, Healthworker healthworker) {
        super(id);
        requireAllNonNull(name, phone, address, orderDate, food);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.food.addAll(food);
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.healthworker = healthworker;
    }

    /**
     * This constructor is used to create a new copy of {@code request}.
     */
    public Request(Request request) {
        this(null, request.name, request.phone, request.address, request.orderDate,
            request.orderStatus, request.food, request.healthworker);
    }

    public Name getName() {
        return name;
    }

    public OrderDate getDate() {
        return this.orderDate;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Healthworker getHealthworker() {
        return healthworker;
    }

    public void setStatusCompleted() {
        orderStatus = new OrderStatus("COMPLETED");
    }
    /**
     * Returns a food set
     */
    public Set<Food> getFood() {
        return food;
    }

    public void setHealthworker(Healthworker newHealthworker) throws RequestLimitExceededException {
        assert(!isAlreadyAssignedDeliveryman());
        healthworker = newHealthworker;
        updateStatusOngoing();
        newHealthworker.addOrder(this);
    }

    private void updateStatusOngoing() {
        orderStatus = new OrderStatus("ONGOING");
    }

    public boolean isAlreadyAssignedDeliveryman() {
        return healthworker != null;
    }

    public boolean isCompleted() {
        return orderStatus.isCompletedStatus();
    }

    public boolean isOngoing() {
        return orderStatus.isOngoingStatus();
    }

    /**
     * Returns true if both orders of the same name have at least one other identity field that is
     * the same. This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Request otherRequest) {
        if (otherRequest == this) {
            return true;
        }

        return otherRequest != null
                && otherRequest.getName().equals(getName())
                && (otherRequest.getPhone().equals(getPhone()))
                && (otherRequest.getDate().equals(getDate()));
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Request)) {
            return false;
        }

        Request otherRequest = (Request) other;
        return otherRequest.getName().equals(getName())
                && otherRequest.getPhone().equals(getPhone())
                && otherRequest.getAddress().equals(getAddress())
                && (otherRequest.getDate().equals(getDate()))
                && otherRequest.getFood().equals(getFood())
                && otherRequest.getOrderStatus().equals(getOrderStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, orderStatus, food);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Address: ")
                .append(getAddress())
                .append(" Date: ")
                .append(getDate())
                .append(" Status: ")
                .append(getOrderStatus())
                .append(" Food: ");
        getFood().forEach(builder::append);
        return builder.toString();
    }

}