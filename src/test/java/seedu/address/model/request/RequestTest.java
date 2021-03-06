package seedu.address.model.request;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BENSON;

import org.junit.Test;

import seedu.address.testutil.RequestBuilder;

public class RequestTest {

    @Test
    public void isSameOrder() {
        // same object -> returns true
        assertTrue(ALICE.isSameOrder(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameOrder(null));

        // different phone -> returns false
        Request editedAlice = new RequestBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));

        // different name -> returns false
        editedAlice = new RequestBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));

        // same name, same phone, same date different address and food -> returns true
        editedAlice = new RequestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
            .withCondition(VALID_FOOD_BURGER).build();
        assertTrue(ALICE.isSameOrder(editedAlice));

        // different data, everything else same -> returns false
        editedAlice = new RequestBuilder(ALICE).withDate("03-10-2018 10:00:01").build();
        assertFalse(ALICE.isSameOrder(editedAlice));

        // same everything, different food -> returns true
        editedAlice = new RequestBuilder(ALICE)
            .withCondition(VALID_FOOD_BURGER).build();
        assertTrue(ALICE.isSameOrder(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Request aliceCopy = new RequestBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different common -> returns false
        assertFalse(ALICE.equals(BENSON));

        // different name -> returns false
        Request editedAlice = new RequestBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new RequestBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new RequestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different food -> returns false
        editedAlice = new RequestBuilder(ALICE).withCondition(VALID_FOOD_BURGER).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
