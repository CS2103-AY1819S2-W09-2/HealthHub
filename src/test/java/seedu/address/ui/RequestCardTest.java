package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOrder;

import org.junit.Test;

import guitests.guihandles.OrderCardHandle;
import seedu.address.model.order.Request;
import seedu.address.testutil.OrderBuilder;

public class RequestCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Request normalRequest = new OrderBuilder().build();
        OrderCard orderCard = new OrderCard(normalRequest, 2);
        uiPartRule.setUiPart(orderCard);
        assertCardDisplay(orderCard, normalRequest, 2);
    }

    @Test
    public void equals() {
        Request request = new OrderBuilder().build();
        OrderCard orderCard = new OrderCard(request, 0);

        // same request, same index -> returns true
        OrderCard copy = new OrderCard(request, 0);
        assertTrue(orderCard.equals(copy));

        // same object -> returns true
        assertTrue(orderCard.equals(orderCard));

        // null -> returns false
        assertFalse(orderCard.equals(null));

        // different types -> returns false
        assertFalse(orderCard.equals(0));

        // different request, same index -> returns false
        Request differentRequest = new OrderBuilder().withName("differentName").build();
        assertFalse(orderCard.equals(new OrderCard(differentRequest, 0)));

        // same request, different index -> returns false
        assertFalse(orderCard.equals(new OrderCard(request, 1)));
    }

    /**
     * Asserts that {@code orderCard} displays the details of {@code expectedRequest} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(OrderCard orderCard, Request expectedRequest, int expectedId) {
        guiRobot.pauseForHuman();

        OrderCardHandle orderCardHandle = new OrderCardHandle(orderCard.getRoot());

        // verify id is displayed correctly
        assertEquals("#" + Integer.toString(expectedId), orderCardHandle.getId());

        // verify common details are displayed correctly
        assertCardDisplaysOrder(expectedRequest, orderCardHandle);
    }
}
