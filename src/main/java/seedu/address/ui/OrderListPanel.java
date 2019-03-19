package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BackToHomeEvent;
import seedu.address.commons.events.ui.HealthWorkerPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToRequestListRequestEvent;
import seedu.address.commons.events.ui.RequestPanelSelectionChangedEvent;
import seedu.address.model.order.Request;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    @FXML
    private ListView<Request> orderListView;

    public OrderListPanel(ObservableList<Request> requestList) {
        super(FXML);

        setConnections(requestList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Request> requestList) {
        orderListView.setItems(requestList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        orderListView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    logger.fine("Selection in request list panel changed to : '" + newValue + "'");
                    raise(new RequestPanelSelectionChangedEvent(newValue));
                }
            });
    }

    /**
     * Scrolls to the {@code OrderCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            orderListView.scrollTo(index);
            orderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToOrderListRequestEvent(JumpToRequestListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleDeliverymanPanelSelectionChangedEvent(HealthWorkerPanelSelectionChangedEvent event) {
        orderListView.getSelectionModel().clearSelection();
    }

    @Subscribe
    private void handleBackToHomeRequest(BackToHomeEvent event) {
        orderListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Request} using a {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<Request> {
        @Override
        protected void updateItem(Request request, boolean empty) {
            super.updateItem(request, empty);

            if (empty || request == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(request, getIndex() + 1).getRoot());
            }
        }
    }

}
