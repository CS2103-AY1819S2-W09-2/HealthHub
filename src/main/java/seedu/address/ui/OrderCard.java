package seedu.address.ui;

import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.order.Food;
import seedu.address.model.order.Request;

/**
 * An UI component that displays information of a {@code Request}.
 */
public class OrderCard extends UiPart<Region> {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ONGOING = "ONGOING";
    public static final String STATUS_COMPLETED = "COMPLETED";
    private static final String FXML = "OrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on OrderBook level 4</a>
     */

    public final Request request;

    @FXML
    private Text id;
    @FXML
    private Label address;
    @FXML
    private Label foodList;
    @FXML
    private Label orderDate;
    @FXML
    private Label orderStatus;

    public OrderCard(Request request, int displayedIndex) {
        super(FXML);
        this.request = request;
        id.setText("#" + displayedIndex);

        address.setText(request.getAddress().value);

        foodList.setText(String.join(", ",
            request.getFood().stream().map(Food::toString).collect(Collectors.toSet())));

        orderDate.setText(request.getDate().toString());

        orderStatus.setText(request.getOrderStatus().toString().substring(0, 1).toUpperCase()
                + request.getOrderStatus().toString().substring(1).toLowerCase());
        setOrderStatusColor();
    }

    private void setOrderStatusColor() {
        orderStatus.getStyleClass().clear();
        if (request.getOrderStatus().toString().equals(STATUS_PENDING)) {
            orderStatus.getStyleClass().add("pending");
        } else if (request.getOrderStatus().toString().equals(STATUS_ONGOING)) {
            orderStatus.getStyleClass().add("ongoing");
        } else if (request.getOrderStatus().toString().equals(STATUS_COMPLETED)) {
            orderStatus.getStyleClass().add("completed");
        }
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderCard)) {
            return false;
        }

        // state check
        OrderCard card = (OrderCard) other;
        return id.getText().equals(card.id.getText())
                && request.equals(card.request);
    }
}
