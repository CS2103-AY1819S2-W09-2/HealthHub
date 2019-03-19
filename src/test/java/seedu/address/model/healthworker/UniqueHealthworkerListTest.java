package seedu.address.model.healthworker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalDeliverymen.RAJUL;
import static seedu.address.testutil.TypicalDeliverymen.YINJING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.common.exceptions.DuplicateHealthWorkerException;
import seedu.address.model.common.exceptions.HealthWorkerNotFoundException;

public class UniqueHealthworkerListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueHealthworkerList uniqueHealthworkerList = new UniqueHealthworkerList();

    @Test
    public void contains_nullDeliverymen_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueHealthworkerList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueHealthworkerList.contains(RAJUL));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueHealthworkerList.add(RAJUL);
        assertTrue(uniqueHealthworkerList.contains(RAJUL));
    }

    @Test
    public void add_nullDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueHealthworkerList.add(null);
    }

    @Test
    public void add_duplicateDeliveryman_throwsDuplicateDeliverymanException() {
        uniqueHealthworkerList.add(RAJUL);
        thrown.expect(DuplicateHealthWorkerException.class);
        uniqueHealthworkerList.add(RAJUL);
    }

    @Test
    public void setDeliveryman_nullTargetDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueHealthworkerList.setDeliveryman(null, RAJUL);
    }

    @Test
    public void setDeliveryman_nullEditedDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueHealthworkerList.setDeliveryman(RAJUL, null);
    }

    @Test
    public void setDeliveryman_targetDeliverymanNotInList_throwsDeliverymanNotFoundException() {
        thrown.expect(HealthWorkerNotFoundException.class);
        uniqueHealthworkerList.setDeliveryman(RAJUL, RAJUL);
    }

    @Test
    public void setDeliveryman_editedDeliverymanIsSameDeliveryman_success() {
        uniqueHealthworkerList.add(RAJUL);
        uniqueHealthworkerList.setDeliveryman(RAJUL, RAJUL);
        UniqueHealthworkerList expectedUniqueHealthworkerList = new UniqueHealthworkerList();
        expectedUniqueHealthworkerList.add(RAJUL);
        assertEquals(expectedUniqueHealthworkerList, uniqueHealthworkerList);
    }

    @Test
    public void setDeliveryman_editedDeliverymanHasDifferentIdentity_success() {
        uniqueHealthworkerList.add(RAJUL);
        uniqueHealthworkerList.setDeliveryman(RAJUL, YINJING);
        UniqueHealthworkerList expectedUniqueHealthworkerList = new UniqueHealthworkerList();
        expectedUniqueHealthworkerList.add(YINJING);
        assertEquals(expectedUniqueHealthworkerList, uniqueHealthworkerList);
    }

    @Test
    public void setDeliveryman_editedDeliverymanHasNonUniqueIdentity_throwsDuplicateDeliverymanException() {
        uniqueHealthworkerList.add(RAJUL);
        uniqueHealthworkerList.add(YINJING);
        thrown.expect(DuplicateHealthWorkerException.class);
        uniqueHealthworkerList.setDeliveryman(RAJUL, YINJING);
    }

    @Test
    public void remove_nullDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueHealthworkerList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsDeliverymanNotFoundException() {
        thrown.expect(HealthWorkerNotFoundException.class);
        uniqueHealthworkerList.remove(RAJUL);
    }

    @Test
    public void remove_existingDeliveryman_removesDeliveryman() {
        uniqueHealthworkerList.add(RAJUL);
        uniqueHealthworkerList.remove(RAJUL);
        UniqueHealthworkerList expectedUniqueHealthworkerList = new UniqueHealthworkerList();
        assertEquals(expectedUniqueHealthworkerList, uniqueHealthworkerList);
    }

    @Test
    public void setDeliverymen_nullUniqueDeliverymenList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueHealthworkerList.setDeliverymen((UniqueHealthworkerList) null);
    }

    @Test
    public void setDeliverymen_uniqueDeliverymenList_replacesOwnListWithProvidedUniqueDeliverymenList() {
        uniqueHealthworkerList.add(RAJUL);
        UniqueHealthworkerList expectedUniqueHealthworkerList = new UniqueHealthworkerList();
        expectedUniqueHealthworkerList.add(YINJING);
        uniqueHealthworkerList.setDeliverymen(expectedUniqueHealthworkerList);
        assertEquals(expectedUniqueHealthworkerList, uniqueHealthworkerList);
    }

    @Test
    public void setDeliverymen_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueHealthworkerList.setDeliverymen((List<Healthworker>) null);
    }

    @Test
    public void setDeliverymen_list_replacesOwnListWithProvidedList() {
        uniqueHealthworkerList.add(RAJUL);
        List<Healthworker> personList = Collections.singletonList(YINJING);
        uniqueHealthworkerList.setDeliverymen(personList);
        UniqueHealthworkerList expectedUniqueHealthworkerList = new UniqueHealthworkerList();
        expectedUniqueHealthworkerList.add(YINJING);
        assertEquals(expectedUniqueHealthworkerList, uniqueHealthworkerList);
    }

    @Test
    public void setDeliverymen_listWithDuplicateDeliverymen_throwsDuplicateDeliverymanException() {
        List<Healthworker> listWithDuplicateDeliverymen = Arrays.asList(RAJUL, RAJUL);
        thrown.expect(DuplicateHealthWorkerException.class);
        uniqueHealthworkerList.setDeliverymen(listWithDuplicateDeliverymen);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueHealthworkerList.asUnmodifiableObservableList().remove(0);
    }

}
