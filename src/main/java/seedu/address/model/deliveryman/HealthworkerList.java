package seedu.address.model.deliveryman;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data into a deliverymen list.
 * Duplicates are not allowed.
 */
public class HealthworkerList {
    private final UniqueHealthworkerList deliverymenList;

    {
        deliverymenList = new UniqueHealthworkerList();
    }

    public HealthworkerList() {
    }

    public HealthworkerList(HealthworkerList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /// list overwrite operations

    /**
     * Replaces the contents of the deliverymen list with {@code deliverymen}.
     * {@code deliverymen} must not contain duplicate persons.
     */
    public void setDeliverymen(List<Healthworker> deliverymen) {
        this.deliverymenList.setDeliverymen(deliverymen);
    }

    /**
     * Resets the contents of the deliverymen list with the contents of {@code newData}.
     * @param newData The HealthworkerList to get the contents from
     */
    public void resetData(HealthworkerList newData) {
        requireNonNull(newData);

        setDeliverymen(newData.getDeliverymenList());
    }

    /// healthworker-level operations

    /**
     * Returns true if the {@code HealthworkerList} contains a {@code healthworker}
     * @param healthworker
     */
    public boolean hasDeliveryman(Healthworker healthworker) {
        requireNonNull(healthworker);
        return deliverymenList.contains(healthworker);
    }

    /**
     * Adds a healthworker to the {@code healthworkerList}
     * @param d
     */
    public void addDeliveryman(Healthworker d) {
        if (d.getTag() == null) {
            d.assignTag();
        }
        deliverymenList.add(d);
    }

    /**
     * Replaces the {@code target} healthworker with an {@code edited} healthworker.
     */
    public void updateDeliveryman(Healthworker target, Healthworker editedD) {
        requireNonNull(editedD);

        if (editedD.getTag() == null) {
            editedD.assignTag();
        }
        deliverymenList.setDeliveryman(target, editedD);
    }

    public void removeDeliveryman(Healthworker key) {
        deliverymenList.remove(key);
    }

    @Override
    public String toString() {
        return deliverymenList.asUnmodifiableObservableList().size() + " deliverymen";
    }

    public ObservableList<Healthworker> getDeliverymenList() {
        return deliverymenList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof HealthworkerList
                    && deliverymenList.equals(((HealthworkerList) other).deliverymenList));
    }

    @Override
    public int hashCode() {
        return deliverymenList.hashCode();
    }
}
