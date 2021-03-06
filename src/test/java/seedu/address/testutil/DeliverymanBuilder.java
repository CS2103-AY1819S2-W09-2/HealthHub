package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.common.Name;
import seedu.address.model.healthworker.Healthworker;
import seedu.address.model.request.Request;

/**
 * A utility class to help build a healthworker
 */
public class DeliverymanBuilder {
    public static final String DEFAULT_NAME = "Deliver E";

    private Name name;
    private UUID id;
    private Set<Request> requests = new HashSet<>();

    public DeliverymanBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the DeliverymanBuilder with the data of {@code healthworkerToCopy}.
     */
    public DeliverymanBuilder(Healthworker healthworkerToCopy) {
        id = healthworkerToCopy.getTag();
        name = healthworkerToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Healthworker} that we are building.
     */
    public DeliverymanBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code id} of the {@code Healthworker} that we are building
     *
     * @param idString
     */
    public DeliverymanBuilder withId(String idString) {
        this.id = UUID.fromString(idString);
        return this;
    }

    /**
     * Add the {@code Request} of the {@code Healthworker} that we are building.
     */
    public DeliverymanBuilder withOrder(Request request) {
        this.requests.add(request);
        return this;
    }

    /**
     * Builds the {@code Healthworker}
     */
    public Healthworker build() {
        if (id != null) {
            return new Healthworker(id, name, requests);
        } else {
            return new Healthworker(name);
        }
    }
}
