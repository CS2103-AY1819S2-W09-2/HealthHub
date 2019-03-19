package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.Request;
import seedu.address.model.order.exceptions.DuplicateRequestException;
import seedu.address.testutil.RequestBuilder;

public class RequestBookTest {

    private final RequestBook orderBook = new RequestBook();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), orderBook.getRequestList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        orderBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyOrderBook_replacesData() {
        RequestBook newData = getTypicalOrderBook();
        orderBook.resetData(newData);
        assertEquals(newData, orderBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Request editedAlice = new RequestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withCondition(VALID_FOOD_BURGER)
            .build();
        List<Request> newRequests = Arrays.asList(ALICE, editedAlice);
        RequestBookStub newData = new RequestBookStub(newRequests);

        thrown.expect(DuplicateRequestException.class);
        orderBook.resetData(newData);
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        orderBook.hasRequest(null);
    }

    @Test
    public void hasOrder_orderNotInOrderBook_returnsFalse() {
        assertFalse(orderBook.hasRequest(ALICE));
    }

    @Test
    public void hasOrder_orderInOrderBook_returnsTrue() {
        orderBook.addRequest(ALICE);
        assertTrue(orderBook.hasRequest(ALICE));
    }

    @Test
    public void hasOrder_orderWithSameIdentityFieldsInOrderBook_returnsTrue() {
        orderBook.addRequest(ALICE);
        Request editedAlice = new RequestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withCondition(VALID_FOOD_BURGER)
            .build();
        assertTrue(orderBook.hasRequest(editedAlice));
    }

    @Test
    public void getOrderList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        orderBook.getRequestList().remove(0);
    }

    /**
     * A stub ReadOnlyRequestBook whose requests list can violate interface constraints.
     */
    private static class RequestBookStub implements ReadOnlyRequestBook {
        private final ObservableList<Request> requests = FXCollections.observableArrayList();

        RequestBookStub(Collection<Request> request) {
            this.requests.setAll(request);
        }

        @Override
        public ObservableList<Request> getRequestList() {
            return requests;
        }
    }

}
