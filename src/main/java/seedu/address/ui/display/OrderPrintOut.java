package seedu.address.ui.display;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.order.Request;
import seedu.address.ui.UiPart;

/**
 * UI Component representing the display of a single request in the healthworker's list of orders.
 */
public class OrderPrintOut extends UiPart<Region> {

    private static final String FXML = "display/OrderPrintOut.fxml";

    private final Request request;

    @FXML
    private Label nameP;

    @FXML
    private Label dateP;

    @FXML
    private Text addressP;

    @FXML
    private Label phoneP;

    public OrderPrintOut(Request request) {
        super(FXML);
        this.request = request;

        nameP.setText("Request from " + request.getName().fullName);
        dateP.setText(request.getDate().toString());
        phoneP.setText(request.getPhone().toString());
        addressP.setText(request.getAddress().toString());
    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderPrintOut)) {
            return false;
        }

        // state check
        OrderPrintOut card = (OrderPrintOut) other;
        return request.equals(card.request);
    }

}



