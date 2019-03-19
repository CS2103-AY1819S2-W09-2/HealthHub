package seedu.address.model.healthworker;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.TaggedObject;
import seedu.address.model.common.Name;
import seedu.address.model.healthworker.exceptions.RequestLimitExceededException;
import seedu.address.model.request.Request;

/**
 * Represents a Healthworker in FoodZoom.
 * Guarantees: has a name that is unique.
 */
public class Healthworker extends TaggedObject {

    /** Limit for amount of requests a healthworker can have at one point of time */
    public static final int ORDERS_LIMIT = 5;

    private final Name name;
    private final Set<Request> requests = new HashSet<>();

    public Healthworker(Name name) {
        this(null, name, null);
    }

    public Healthworker(Name name, Set<Request> requests) {
        this(null, name, requests);
    }

    /** This constructor is used when the {@code id} is specified. */
    public Healthworker(UUID id, Name name, Set<Request> requests) {
        super(id);
        requireAllNonNull(name);
        this.name = name;
        if (requests != null) {
            this.requests.addAll(requests);
        }
    }

    /**
     * This constructor is used to create a new copy of {@code healthworker}.
     */
    public Healthworker(Healthworker healthworker) {
        this(null, healthworker.name, healthworker.requests);
    }

    public Name getName() {
        return name;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    /**
     * Adds {@code request} to the set of requests for the healthworker.
     * Throws {@code RequestLimitExceededException} if the amount of requests assigned exceeds the limit for requests.
     */
    public void addOrder(Request request) throws RequestLimitExceededException {
        if (requests.size() >= ORDERS_LIMIT) {
            throw new RequestLimitExceededException();
        }
        requests.add(request);
    }

    public boolean canAccommodate(Collection<Request> requests) {
        return getRequests().size() + requests.size() <= ORDERS_LIMIT;
    }

    public void removeOrder(Request request) {
        requests.remove(request);
    }

    public boolean hasOrders() {
        return !requests.isEmpty();
    }

    /**
     * Returns if this is the same common as {@code other}
     */
    public boolean isSameDeliveryman(Healthworker other) {
        if (other == this) {
            return true;
        }

        return other != null && other.getName().equals(getName());
    }

    /**
     * Returns true if both deliverymen have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Healthworker)) {
            return false;
        }

        Healthworker otherPerson = (Healthworker) other;
        return otherPerson.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
