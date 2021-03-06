package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalOrders.getTypicalOrders;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOrder;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.Test;

import guitests.guihandles.RequestCardHandle;
import guitests.guihandles.OrderListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToRequestListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.request.Request;
import seedu.address.storage.XmlHealthHub;

public class RequestListPanelTest extends GuiUnitTest {
    private static final ObservableList<Request> TYPICAL_REQUESTS =
        FXCollections.observableList(getTypicalOrders());

    private static final JumpToRequestListRequestEvent JUMP_TO_SECOND_EVENT =
        new JumpToRequestListRequestEvent(INDEX_SECOND);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private OrderListPanelHandle orderListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_REQUESTS);

        for (int i = 0; i < TYPICAL_REQUESTS.size(); i++) {
            orderListPanelHandle.navigateToCard(TYPICAL_REQUESTS.get(i));
            Request expectedRequest = TYPICAL_REQUESTS.get(i);
            RequestCardHandle actualCard = orderListPanelHandle.getOrderCardHandle(i);

            assertCardDisplaysOrder(expectedRequest, actualCard);
            assertEquals("#" + Integer.toString(i + 1), actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_REQUESTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        RequestCardHandle expectedPerson = orderListPanelHandle.getOrderCardHandle(INDEX_SECOND.getZeroBased());
        RequestCardHandle selectedPerson = orderListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code OrderListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Request> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of common cards exceeded time limit");
    }

    /**
     * Returns a list of orders containing {@code orderCount} orders that is used to populate the
     * {@code OrderListPanel}.
     */
    private ObservableList<Request> createBackingList(int orderCount) throws Exception {
        Path xmlFile = createXmlFileWithOrders(orderCount);
        XmlHealthHub xmlHealthHub =
            XmlUtil.getDataFromFile(xmlFile, XmlHealthHub.class);
        return FXCollections.observableArrayList(xmlHealthHub.getOrderBook().getRequestList());
    }

    /**
     * Returns a .xml file containing {@code orderCount} orders. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithOrders(int orderCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<healthhub>\n");
        builder.append("<orderbook>\n");
        for (int i = 0; i < orderCount; i++) {
            builder.append("<orders tag=\"" + UUID.randomUUID().toString() + "\">\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<address>a 612234</address>\n");
            builder.append("<date>01-10-2018 10:00:00</date>\n");
            builder.append("<status>PENDING</status>\n");
            builder.append("<food>milo</food>\n");
            builder.append("</orders>\n");
        }
        builder.append("</orderbook>\n");
        builder.append("<healthworkerList>\n");
        builder.append("</healthworkerList>\n");
        builder.append("</healthhub>\n");

        Path manyOrdersFile = Paths.get(TEST_DATA_FOLDER + "manyOrders.xml");
        FileUtil.createFile(manyOrdersFile);
        FileUtil.writeToFile(manyOrdersFile, builder.toString());
        manyOrdersFile.toFile().deleteOnExit();
        return manyOrdersFile;
    }

    /**
     * Initializes {@code orderListPanelHandle} with a {@code OrderListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code OrderListPanel}.
     */
    private void initUi(ObservableList<Request> backingList) {
        OrderListPanel orderListPanel = new OrderListPanel(backingList);
        uiPartRule.setUiPart(orderListPanel);

        orderListPanelHandle = new OrderListPanelHandle(getChildNode(orderListPanel.getRoot(),
            OrderListPanelHandle.ORDER_LIST_VIEW_ID));
    }
}
