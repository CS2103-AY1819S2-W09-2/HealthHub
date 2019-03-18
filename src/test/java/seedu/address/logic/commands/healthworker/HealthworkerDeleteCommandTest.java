package seedu.address.logic.commands.healthworker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeliverymanAtIndex;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliveryman.Healthworker;

public class HealthworkerDeleteCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(), getTypicalDeliverymenList(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Healthworker healthworkerToDelete = model.getFilteredDeliverymenList().get(INDEX_FIRST.getZeroBased());
        HealthWorkerDeleteCommand deleteCommand = new HealthWorkerDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(HealthWorkerDeleteCommand.MESSAGE_DELETE_DELIVERYMAN_SUCCESS,
            healthworkerToDelete);

        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.deleteDeliveryman(healthworkerToDelete);
        expectedModel.commitDeliverymenList();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeliverymenList().size() + 1);
        HealthWorkerDeleteCommand deleteCommand = new HealthWorkerDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDeliverymanAtIndex(model, INDEX_FIRST);

        Healthworker healthworkerToDelete = model.getFilteredDeliverymenList().get(INDEX_FIRST.getZeroBased());
        HealthWorkerDeleteCommand deleteCommand = new HealthWorkerDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(HealthWorkerDeleteCommand.MESSAGE_DELETE_DELIVERYMAN_SUCCESS,
            healthworkerToDelete);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.deleteDeliveryman(healthworkerToDelete);
        expectedModel.commitDeliverymenList();
        showNoDeliveryman(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDeliverymanAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDeliverymenList().getDeliverymenList().size());

        HealthWorkerDeleteCommand deleteCommand = new HealthWorkerDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        HealthWorkerDeleteCommand deleteFirstCommand = new HealthWorkerDeleteCommand(INDEX_FIRST);
        HealthWorkerDeleteCommand deleteSecondCommand = new HealthWorkerDeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        HealthWorkerDeleteCommand deleteFirstCommandCopy = new HealthWorkerDeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different common -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDeliveryman(Model model) {
        model.updateFilteredDeliverymenList(p -> false);

        assertTrue(model.getFilteredDeliverymenList().isEmpty());
    }
}
