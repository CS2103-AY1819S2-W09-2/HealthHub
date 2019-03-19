package seedu.address.logic.commands.request;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Request;
import seedu.address.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(),
                getTypicalDeliverymenList(), new UserPrefs());
    }

    @Test
    public void execute_newOrder_success() {
        Request validRequest = new OrderBuilder().build();
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.addOrder(validRequest);
        expectedModel.commitOrderBook();

        assertCommandSuccess(new AddCommand(validRequest), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validRequest), expectedModel);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Request requestInList = model.getOrderBook().getOrderList().get(0);
        assertCommandFailure(new AddCommand(requestInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_ORDER);
    }

}
