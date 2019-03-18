package seedu.address.testutil;

import seedu.address.model.deliveryman.Healthworker;
import seedu.address.model.deliveryman.HealthworkerList;

/**
 * A utility class to help with building HealthworkerList objects.
 */
public class DeliverymenListBuilder {

    private HealthworkerList healthworkerList;

    public DeliverymenListBuilder() {
        healthworkerList = new HealthworkerList();
    }

    public DeliverymenListBuilder(HealthworkerList healthworkerList) {
        this.healthworkerList = healthworkerList;
    }

    /**
     * Adds a new {@code Healthworker} to the {@code HealthworkerList} that we are building.
     */
    public DeliverymenListBuilder withDeliveryman(Healthworker healthworker) {
        healthworkerList.addDeliveryman(healthworker);
        return this;
    }

    public HealthworkerList build() {
        return healthworkerList;
    }
}
