package seedu.address.model.deliveryman;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.common.exceptions.DuplicateHealthWorkerException;
import seedu.address.model.common.exceptions.HealthWorkerNotFoundException;

/**
 * A list of unique deliverymen
 */
public class UniqueDeliverymenList implements Iterable<Deliveryman> {
    private final ObservableList<Deliveryman> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent common as the given argument.
     */
    public boolean contains(Deliveryman toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDeliveryman);
    }

    /**
     * Adds healthworker to the list.
     * The healthworker must not already exist in the list.
     */
    public void add(Deliveryman toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            //TODO: add appropriate exception for this
            throw new DuplicateHealthWorkerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Remove the equivalent healthworker from the list.
     * healthworker must exist in the list.
     */
    public void remove(Deliveryman toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new HealthWorkerNotFoundException();
        }
    }

    /**
     * Replaces the healthworker {@code target} in the list with {@code edited}.
     * {@code target} must exist in the list.
     * The healthworker identity of {@code edited} must not be the same as another healthworker in the list.
     */
    public void setDeliveryman(Deliveryman target, Deliveryman edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            //TODO: add appropriate exception
            throw new HealthWorkerNotFoundException();
        }

        if (!target.isSameDeliveryman(edited) && contains(edited)) {
            throw new DuplicateHealthWorkerException();
        }

        internalList.set(index, edited);
    }

    public void setDeliverymen(UniqueDeliverymenList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setDeliverymen(List<Deliveryman> deliverymen) {
        requireAllNonNull(deliverymen);

        if (!deliverymenAreUnique(deliverymen)) {
            throw new DuplicateHealthWorkerException();
        }

        internalList.setAll(deliverymen);
    }

    public ObservableList<Deliveryman> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Deliveryman> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueDeliverymenList
                    && internalList.equals(((UniqueDeliverymenList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if all the deliverymen in the list are unique.
     */
    private boolean deliverymenAreUnique(List<Deliveryman> deliverymen) {
        for (int i = 0; i < deliverymen.size() - 1; i++) {
            for (int j = i + 1; j < deliverymen.size(); j++) {
                if (deliverymen.get(i).isSameDeliveryman(deliverymen.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
