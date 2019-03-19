package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOrder;

import org.junit.Test;

import guitests.guihandles.RequestCardHandle;
import seedu.address.model.request.Request;
import seedu.address.testutil.RequestBuilder;

public class RequestCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Request normalRequest = new RequestBuilder().build();
        RequestCard requestCard = new RequestCard(normalRequest, 2);
        uiPartRule.setUiPart(requestCard);
        assertCardDisplay(requestCard, normalRequest, 2);
    }

    @Test
    public void equals() {
        Request request = new RequestBuilder().build();
        RequestCard requestCard = new RequestCard(request, 0);

        // same request, same index -> returns true
        RequestCard copy = new RequestCard(request, 0);
        assertTrue(requestCard.equals(copy));

        // same object -> returns true
        assertTrue(requestCard.equals(requestCard));

        // null -> returns false
        assertFalse(requestCard.equals(null));

        // different types -> returns false
        assertFalse(requestCard.equals(0));

        // different request, same index -> returns false
        Request differentRequest = new RequestBuilder().withName("differentName").build();
        assertFalse(requestCard.equals(new RequestCard(differentRequest, 0)));

        // same request, different index -> returns false
        assertFalse(requestCard.equals(new RequestCard(request, 1)));
    }

    /**
     * Asserts that {@code requestCard} displays the details of {@code expectedRequest} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RequestCard requestCard, Request expectedRequest, int expectedId) {
        guiRobot.pauseForHuman();

        RequestCardHandle requestCardHandle = new RequestCardHandle(requestCard.getRoot());

        // verify id is displayed correctly
        assertEquals("#" + Integer.toString(expectedId), requestCardHandle.getId());

        // verify common details are displayed correctly
        assertCardDisplaysOrder(expectedRequest, requestCardHandle);
    }
}
