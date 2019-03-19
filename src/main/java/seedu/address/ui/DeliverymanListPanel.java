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
import seedu.address.commons.events.ui.JumpToHealthWorkerListRequestEvent;
import seedu.address.commons.events.ui.RequestPanelSelectionChangedEvent;
import seedu.address.model.deliveryman.Healthworker;

/**
 * Panel containing the list of orders.
 */
public class DeliverymanListPanel extends UiPart<Region> {
    private static final String FXML = "DeliverymanListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeliverymanListPanel.class);

    @FXML
    private ListView<Healthworker> deliverymanListView;

    public DeliverymanListPanel(ObservableList<Healthworker> deliveryManList) {
        super(FXML);

        setConnections(deliveryManList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Healthworker> orderList) {
        deliverymanListView.setItems(orderList);
        deliverymanListView.setCellFactory(listView -> new DeliveryManListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        deliverymanListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in delivery man list panel changed to : '" + newValue + "'");
                        raise(new HealthWorkerPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code OrderCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            deliverymanListView.scrollTo(index);
            deliverymanListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToDeliveryManListRequestEvent(JumpToHealthWorkerListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleOrderPanelSelectionChangedEvent(RequestPanelSelectionChangedEvent event) {
        deliverymanListView.getSelectionModel().clearSelection();
    }

    @Subscribe
    private void handleBackToHomeRequest(BackToHomeEvent event) {
        deliverymanListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Request} using a {@code OrderCard}.
     */
    class DeliveryManListViewCell extends ListCell<Healthworker> {
        @Override
        protected void updateItem(Healthworker healthworker, boolean empty) {
            super.updateItem(healthworker, empty);

            if (empty || healthworker == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeliverymanCard(healthworker, getIndex() + 1).getRoot());
            }
        }
    }

}
