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
public class RequestCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String CONDITION_FIELD_ID = "#conditionList";

    private final Text idLabel;
    private final Label addressLabel;
    private final Label conditionLabel;

    public RequestCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);

        conditionLabel = getChildNode(CONDITION_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public List<String> getConditions() {
        return Arrays.asList(conditionLabel.getText().split(", "));
    }

    /**
     * Returns true if this handle contains {@code request}.
     */
    public boolean equals(Request request) {
        return getAddress().equals(request.getAddress().value)
            && ImmutableMultiset.copyOf(getConditions()).equals(ImmutableMultiset.copyOf(request
            .getCondition().stream()
            .map(food -> food.foodName)
            .collect(Collectors.toList())));
    }
}
