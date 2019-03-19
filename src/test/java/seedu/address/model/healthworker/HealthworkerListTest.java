package seedu.address.model.healthworker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalDeliverymen.MANIKA;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HealthworkerListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final HealthworkerList healthworkerList = new HealthworkerList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), healthworkerList.getDeliverymenList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        healthworkerList.resetData(null);
    }

    @Test
    public void resetData_withValidReadyOnlyDeliverymenList_replacesData() {
        HealthworkerList newData = getTypicalDeliverymenList();
        healthworkerList.resetData(newData);
        assertEquals(newData, healthworkerList);
    }

    @Test
    public void hasDeliveryman_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        healthworkerList.hasDeliveryman(null);
    }

    @Test
    public void hasDeliveryman_personNotInAddressBook_returnsFalse() {
        assertFalse(healthworkerList.hasDeliveryman(MANIKA));
    }

    @Test
    public void hasDeliveryman_deliverymanInDeliverymenList_returnsTrue() {
        healthworkerList.addDeliveryman(MANIKA);
        assertTrue(healthworkerList.hasDeliveryman(MANIKA));
    }

    @Test
    public void getDeliverymenList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        healthworkerList.getDeliverymenList().remove(0);
    }

    /**
     * A stub HealthworkerList whose deliverymen list can violate interface constraints.
     */
    private static class HealthworkerListStub extends HealthworkerList {
        private final ObservableList<Healthworker> deliverymen = FXCollections.observableArrayList();

        HealthworkerListStub(Collection<Healthworker> deliverymen) {
            this.deliverymen.setAll(deliverymen);
        }

        @Override
        public ObservableList<Healthworker> getDeliverymenList() {
            return deliverymen;
        }
    }
}
