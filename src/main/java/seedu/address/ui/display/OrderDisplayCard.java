package seedu.address.ui.display;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.healthworker.Healthworker;
import seedu.address.model.request.Request;
import seedu.address.ui.UiPart;

/**
 * UI Component that represents the display for an Request
 */
public class OrderDisplayCard extends UiPart<Region> {
    private static final String FXML = "display/OrderDisplayCard.fxml";

    public final Request request;

    @FXML
    private Label foodL;
    @FXML
    private Label dateL;
    @FXML
    private Label phoneL;
    @FXML
    private Text addressL;
    @FXML
    private Label nameL;
    @FXML
    private Label deliverymanL;

    public OrderDisplayCard(Request request) {
        super(FXML);
        this.request = request;

        nameL.setText("Name: " + request.getName().fullName);
        dateL.setText("Date: " + request.getDate().toString());
        foodL.setText("Condition: " + request.getCondition().toString());
        phoneL.setText("Phone: " + request.getPhone().toString());
        addressL.setText("Address: " + request.getAddress().toString());
        deliverymanL.setText("Healthworker: " + getFullNameOrNull(request.getHealthworker()));
    }

    private String getFullNameOrNull(Healthworker healthworker) {
        if (healthworker == null) {
            return "Not assigned.";
        }
        return healthworker.getName().fullName;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderDisplayCard)) {
            return false;
        }

        // state check
        OrderDisplayCard card = (OrderDisplayCard) other;
        return request.equals(card.request);
    }
}
