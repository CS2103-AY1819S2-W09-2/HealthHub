package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.order.exceptions.DuplicateRequestException;
import seedu.address.model.order.exceptions.RequestNotFoundException;
import seedu.address.testutil.OrderBuilder;

public class UniqueRequestListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRequestList uniqueRequestList = new UniqueRequestList();

    @Test
    public void contains_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRequestList.contains(null);
    }

    @Test
    public void contains_orderNotInList_returnsFalse() {
        assertFalse(uniqueRequestList.contains(ALICE));
    }

    @Test
    public void contains_orderInList_returnsTrue() {
        uniqueRequestList.add(ALICE);
        assertTrue(uniqueRequestList.contains(ALICE));
    }

    @Test
    public void contains_orderWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRequestList.add(ALICE);
        Request editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withFood(VALID_FOOD_BURGER)
                .build();
        assertTrue(uniqueRequestList.contains(editedAlice));
    }

    @Test
    public void add_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRequestList.add(null);
    }

    @Test
    public void add_duplicateOrder_throwsDuplicateOrderException() {
        uniqueRequestList.add(ALICE);
        thrown.expect(DuplicateRequestException.class);
        uniqueRequestList.add(ALICE);
    }

    @Test
    public void setOrder_nullTargetOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRequestList.setOrder(null, ALICE);
    }

    @Test
    public void setOrder_nullEditedOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRequestList.setOrder(ALICE, null);
    }

    @Test
    public void setOrder_targetOrderNotInList_throwsOrderNotFoundException() {
        thrown.expect(RequestNotFoundException.class);
        uniqueRequestList.setOrder(ALICE, ALICE);
    }

    @Test
    public void setOrder_editedOrderIsSameOrder_success() {
        uniqueRequestList.add(ALICE);
        uniqueRequestList.setOrder(ALICE, ALICE);
        UniqueRequestList expectedUniqueRequestList = new UniqueRequestList();
        expectedUniqueRequestList.add(ALICE);
        assertEquals(expectedUniqueRequestList, uniqueRequestList);
    }

    @Test
    public void setOrder_editedOrderHasSameIdentity_success() {
        uniqueRequestList.add(ALICE);
        Request editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withFood(VALID_FOOD_BURGER)
                .build();
        uniqueRequestList.setOrder(ALICE, editedAlice);
        UniqueRequestList expectedUniqueRequestList = new UniqueRequestList();
        expectedUniqueRequestList.add(editedAlice);
        assertEquals(expectedUniqueRequestList, uniqueRequestList);
    }

    @Test
    public void setOrder_editedOrderHasDifferentIdentity_success() {
        uniqueRequestList.add(ALICE);
        uniqueRequestList.setOrder(ALICE, BOB);
        UniqueRequestList expectedUniqueRequestList = new UniqueRequestList();
        expectedUniqueRequestList.add(BOB);
        assertEquals(expectedUniqueRequestList, uniqueRequestList);
    }

    @Test
    public void setOrder_editedOrderHasNonUniqueIdentity_throwsDuplicateOrderException() {
        uniqueRequestList.add(ALICE);
        uniqueRequestList.add(BOB);
        thrown.expect(DuplicateRequestException.class);
        uniqueRequestList.setOrder(ALICE, BOB);
    }

    @Test
    public void remove_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRequestList.remove(null);
    }

    @Test
    public void remove_orderDoesNotExist_throwsOrderNotFoundException() {
        thrown.expect(RequestNotFoundException.class);
        uniqueRequestList.remove(ALICE);
    }

    @Test
    public void remove_existingOrder_removesOrder() {
        uniqueRequestList.add(ALICE);
        uniqueRequestList.remove(ALICE);
        UniqueRequestList expectedUniqueRequestList = new UniqueRequestList();
        assertEquals(expectedUniqueRequestList, uniqueRequestList);
    }

    @Test
    public void setOrder_nullUniqueOrderList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRequestList.setOrder((UniqueRequestList) null);
    }

    @Test
    public void setOrder_uniqueOrderList_replacesOwnListWithProvidedUniqueOrderList() {
        uniqueRequestList.add(ALICE);
        UniqueRequestList expectedUniqueRequestList = new UniqueRequestList();
        expectedUniqueRequestList.add(BOB);
        uniqueRequestList.setOrder(expectedUniqueRequestList);
        assertEquals(expectedUniqueRequestList, uniqueRequestList);
    }

    @Test
    public void setOrder_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRequestList.setOrder((List<Request>) null);
    }

    @Test
    public void setOrder_list_replacesOwnListWithProvidedList() {
        uniqueRequestList.add(ALICE);
        List<Request> requestList = Collections.singletonList(BOB);
        uniqueRequestList.setOrder(requestList);
        UniqueRequestList expectedUniqueRequestList = new UniqueRequestList();
        expectedUniqueRequestList.add(BOB);
        assertEquals(expectedUniqueRequestList, uniqueRequestList);
    }

    @Test
    public void setOrder_listWithDuplicateOrders_throwsDuplicatePersonException() {
        List<Request> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateRequestException.class);
        uniqueRequestList.setOrder(listWithDuplicatePersons);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRequestList.asUnmodifiableObservableList().remove(0);
    }
}
