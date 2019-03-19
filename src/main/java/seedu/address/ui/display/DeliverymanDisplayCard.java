package seedu.address.ui.display;

import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.healthworker.Healthworker;
import seedu.address.model.request.Request;
import seedu.address.ui.UiPart;

/**
 * UI Component that displays relevant information of a healthworker.
 */
public class DeliverymanDisplayCard extends UiPart<Region> {
    private static final String FXML = "display/DeliverymanDisplayCard.fxml";
    private static final String BUSY_LABEL_CLASS = "busy";
    private static final String AVAILABLE_LABEL_CLASS = "available";

    public final Healthworker healthworker;

    @FXML
    private Label namePrintOut;
    @FXML
    private Label deliverymanStatusPrintOut;
    @FXML
    private VBox orderListDisplay;


    public DeliverymanDisplayCard(Healthworker healthworker) {
        super(FXML);
        this.healthworker = healthworker;
        namePrintOut.setText(healthworker.getName().fullName);

        setDeliverymanStatus();
        setOrders(healthworker.getRequests());
    }

    private void setDeliverymanStatus() {
        if (healthworker.getRequests().size() > 0) {
            deliverymanStatusPrintOut.setText("Delivering");
            deliverymanStatusPrintOut.getStyleClass().clear();
            deliverymanStatusPrintOut.getStyleClass().add(BUSY_LABEL_CLASS);
        } else {
            deliverymanStatusPrintOut.setText("Available");
            deliverymanStatusPrintOut.getStyleClass().clear();
            deliverymanStatusPrintOut.getStyleClass().add(AVAILABLE_LABEL_CLASS);
        }
    }

    private void setOrders(Set<Request> requests) {
        for (Request o : requests) {

            OrderPrintOut orderPrintOut = new OrderPrintOut(o);
            orderListDisplay.getChildren().add(orderPrintOut.getRoot());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliverymanDisplayCard)) {
            return false;
        }

        // state check
        DeliverymanDisplayCard card = (DeliverymanDisplayCard) other;
        return healthworker.equals(card.healthworker);
    }
}
