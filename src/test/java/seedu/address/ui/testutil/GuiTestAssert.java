package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.RequestCardHandle;
import guitests.guihandles.OrderListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.request.Request;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(RequestCardHandle expectedCard, RequestCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getConditions(), actualCard.getConditions());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysOrder(Request expectedRequest, RequestCardHandle actualCard) {
        assertEquals(expectedRequest.getAddress().value, actualCard.getAddress());
        assertEquals(expectedRequest.getCondition().stream().map(f -> f.foodName).collect(Collectors.toList()),
            actualCard.getConditions());
    }

    /**
     * Asserts that the list in {@code orderListPanelHandle} displays the details of {@code requests} correctly and
     * in the correct request.
     */
    public static void assertListMatching(OrderListPanelHandle orderListPanelHandle, Request... requests) {
        for (int i = 0; i < requests.length; i++) {
            orderListPanelHandle.navigateToCard(i);
            assertCardDisplaysOrder(requests[i], orderListPanelHandle.getOrderCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code orderListPanelHandle} displays the details of {@code requests} correctly and
     * in the correct request.
     */
    public static void assertListMatching(OrderListPanelHandle orderListPanelHandle, List<Request> requests) {
        assertListMatching(orderListPanelHandle, requests.toArray(new Request[0]));
    }

    /**
     * Asserts the size of the list in {@code orderListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(OrderListPanelHandle orderListPanelHandle, int size) {
        int numberOfPeople = orderListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
