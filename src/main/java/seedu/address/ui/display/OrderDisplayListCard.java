package seedu.address.ui.display;

import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.order.Food;
import seedu.address.model.order.Request;
import seedu.address.ui.UiPart;

/**
 * UI Component representing the display of a single request in a list.
 */
public class OrderDisplayListCard extends UiPart<Region> {
    private static final String FXML = "display/OrderDisplayListCard.fxml";
    private static final String NAME_LABEL_FORMAT = "Contact: %1$s (%2$s)";
    private static final String FOOD_LABEL_FORMAT = "Request: %s";

    public final Request request;

    @FXML
    private Label address;
    @FXML
    private Label name;
    @FXML
    private Label food;

    public OrderDisplayListCard(Request request) {
        super(FXML);
        this.request = request;
        address.setText(request.getAddress().toString());
        name.setText(String.format(NAME_LABEL_FORMAT, request.getName().fullName, request.getPhone().toString()));

        food.setText(String.format(FOOD_LABEL_FORMAT, String.join(", ",
            request.getFood().stream().map(Food::toString).collect(Collectors.toSet()))));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderDisplayListCard)) {
            return false;
        }

        // state check
        OrderDisplayListCard card = (OrderDisplayListCard) other;
        return request.equals(card.request);
    }
}
