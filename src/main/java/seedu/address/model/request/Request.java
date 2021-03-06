package seedu.address.model.request;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.TaggedObject;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.healthworker.Healthworker;
import seedu.address.model.healthworker.exceptions.RequestLimitExceededException;

/**
 * Represents an Request in the request book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Request extends TaggedObject {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Address address;
    private final RequestDate requestDate;
    private final Set<Condition> condition = new HashSet<>();
    private RequestStatus requestStatus;
    private Healthworker healthworker;

    /**
     * Every field must be present and not null.
     */
    public Request(Name name, Phone phone, Address address, RequestDate requestDate,
                   Set<Condition> condition) {
        this(null, name, phone, address, requestDate, new RequestStatus(), condition, null);
    }

    /**
     * Every field must be present and not null.
     */
    public Request(Name name, Phone phone, Address address, RequestDate requestDate, RequestStatus requestStatus,
                   Set<Condition> condition) {
        this(null, name, phone, address, requestDate, requestStatus, condition, null);
    }

    /**
     * Every field must be present and not null besides healthworker.
     */
    public Request(Name name, Phone phone, Address address, RequestDate requestDate, RequestStatus requestStatus,
                   Set<Condition> condition, Healthworker healthworker) {
        this(null, name, phone, address, requestDate, requestStatus, condition, healthworker);
    }

    /**
     * This constructor is used to create an {@code request} with a specified id.
     */
    public Request(UUID id, Name name, Phone phone, Address address, RequestDate requestDate,
                   RequestStatus requestStatus, Set<Condition> condition, Healthworker healthworker) {
        super(id);
        requireAllNonNull(name, phone, address, requestDate, condition);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.condition.addAll(condition);
        this.requestDate = requestDate;
        this.requestStatus = requestStatus;
        this.healthworker = healthworker;
    }

    /**
     * This constructor is used to create a new copy of {@code request}.
     */
    public Request(Request request) {
        this(null, request.name, request.phone, request.address, request.requestDate,
            request.requestStatus, request.condition, request.healthworker);
    }

    public Name getName() {
        return name;
    }

    public RequestDate getDate() {
        return this.requestDate;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public Healthworker getHealthworker() {
        return healthworker;
    }

    public void setStatusCompleted() {
        requestStatus = new RequestStatus("COMPLETED");
    }
    /**
     * Returns a condition set
     */
    public Set<Condition> getCondition() {
        return condition;
    }

    public void setHealthworker(Healthworker newHealthworker) throws RequestLimitExceededException {
        assert(!isAlreadyAssignedDeliveryman());
        healthworker = newHealthworker;
        updateStatusOngoing();
        newHealthworker.addOrder(this);
    }

    private void updateStatusOngoing() {
        requestStatus = new RequestStatus("ONGOING");
    }

    public boolean isAlreadyAssignedDeliveryman() {
        return healthworker != null;
    }

    public boolean isCompleted() {
        return requestStatus.isCompletedStatus();
    }

    public boolean isOngoing() {
        return requestStatus.isOngoingStatus();
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
                && otherRequest.getCondition().equals(getCondition())
                && otherRequest.getRequestStatus().equals(getRequestStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, requestStatus, condition);
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
                .append(getRequestStatus())
                .append(" Condition: ");
        getCondition().forEach(builder::append);
        return builder.toString();
    }

}
