package guitests.guihandles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import seedu.address.model.order.Request;

/**
 * Provides a handle to a request card in the request list panel.
 */
public class OrderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String FOOD_FIELD_ID = "#foodList";

    private final Text idLabel;
    private final Label addressLabel;
    private final Label foodLabel;

    public OrderCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);

        foodLabel = getChildNode(FOOD_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public List<String> getFood() {
        return Arrays.asList(foodLabel.getText().split(", "));
    }

    /**
     * Returns true if this handle contains {@code request}.
     */
    public boolean equals(Request request) {
        return getAddress().equals(request.getAddress().value)
                && ImmutableMultiset.copyOf(getFood()).equals(ImmutableMultiset.copyOf(request.getFood().stream()
                .map(food -> food.foodName)
                .collect(Collectors.toList())));
    }
}
