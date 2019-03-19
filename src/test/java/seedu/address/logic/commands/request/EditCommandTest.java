package seedu.address.logic.commands.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.request.EditCommand.EditOrderDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.request.Request;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.RequestBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(),
        getTypicalDeliverymenList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Request editedRequest = new RequestBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedRequest).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedRequest);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
            model.getDeliverymenList(), new UserPrefs());
        expectedModel.updateOrder(model.getFilteredOrderList().get(0), editedRequest);
        expectedModel.commitOrderBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Request lastRequest = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        RequestBuilder orderInList = new RequestBuilder(lastRequest);
        Request editedRequest = orderInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withCondition(VALID_FOOD_BURGER).build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withFood(VALID_FOOD_BURGER).build();
        EditCommand editCommand = new EditCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedRequest);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
            model.getDeliverymenList(), new UserPrefs());
        expectedModel.updateOrder(lastRequest, editedRequest);
        expectedModel.commitOrderBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST, new EditOrderDescriptor());
        Request editedRequest = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedRequest);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
            model.getDeliverymenList(), new UserPrefs());
        expectedModel.commitOrderBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST);

        Request requestInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        Request editedRequest = new RequestBuilder(requestInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
            new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedRequest);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
            model.getDeliverymenList(), new UserPrefs());
        expectedModel.updateOrder(model.getFilteredOrderList().get(0), editedRequest);
        expectedModel.commitOrderBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateOrderUnfilteredList_failure() {
        Request firstRequest = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(firstRequest).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_duplicateOrderFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST);

        // edit request in filtered list into a duplicate in request book
        Request requestInList = model.getOrderBook().getRequestList().get(INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
            new EditOrderDescriptorBuilder(requestInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidOrderIndexFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;

        // ensures that outOfBoundIndex is still in bounds of request book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getRequestList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
            new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Request editedRequest = new RequestBuilder().build();
        Request requestToEdit = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedRequest).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
            model.getDeliverymenList(), new UserPrefs());
        expectedModel.updateOrder(requestToEdit, editedRequest);
        expectedModel.commitOrderBook();

        // edit -> first request edited
        editCommand.execute(model, commandHistory);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> request book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexFilteredList_sameOrderEdited() throws Exception {
        Request editedRequest = new RequestBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedRequest).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
            model.getDeliverymenList(), new UserPrefs());

        showOrderAtIndex(model, INDEX_SECOND);
        Request requestToEdit = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        expectedModel.updateOrder(requestToEdit, editedRequest);
        expectedModel.commitOrderBook();

        // edit -> edits second request in unfiltered request list / first request in filtered request list
        editCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST, DESC_BOB)));
    }

}
