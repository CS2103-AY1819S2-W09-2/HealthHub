package seedu.address.ui;

import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.request.Condition;
import seedu.address.model.request.Request;

/**
 * An UI component that displays information of a {@code Request}.
 */
public class RequestCard extends UiPart<Region> {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ONGOING = "ONGOING";
    public static final String STATUS_COMPLETED = "COMPLETED";
    private static final String FXML = "OrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RequestBook level 4</a>
     */

    public final Request request;

    @FXML
    private Text id;
    @FXML
    private Label address;
    @FXML
    private Label conditionList;
    @FXML
    private Label requestDate;
    @FXML
    private Label requestStatus;

    public RequestCard(Request request, int displayedIndex) {
        super(FXML);
        this.request = request;
        id.setText("#" + displayedIndex);

        address.setText(request.getAddress().value);

        conditionList.setText(String.join(", ",
            request.getCondition().stream().map(Condition::toString).collect(Collectors.toSet())));

        requestDate.setText(request.getDate().toString());

        requestStatus.setText(request.getRequestStatus().toString().substring(0, 1).toUpperCase()
            + request.getRequestStatus().toString().substring(1).toLowerCase());
        setOrderStatusColor();
    }

    private void setOrderStatusColor() {
        requestStatus.getStyleClass().clear();
        if (request.getRequestStatus().toString().equals(STATUS_PENDING)) {
            requestStatus.getStyleClass().add("pending");
        } else if (request.getRequestStatus().toString().equals(STATUS_ONGOING)) {
            requestStatus.getStyleClass().add("ongoing");
        } else if (request.getRequestStatus().toString().equals(STATUS_COMPLETED)) {
            requestStatus.getStyleClass().add("completed");
        }
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RequestCard)) {
            return false;
        }

        // state check
        RequestCard card = (RequestCard) other;
        return id.getText().equals(card.id.getText())
            && request.equals(card.request);
    }
}
