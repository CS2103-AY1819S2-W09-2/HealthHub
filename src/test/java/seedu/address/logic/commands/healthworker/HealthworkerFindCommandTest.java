package seedu.address.logic.commands.healthworker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DELIVERYMEN_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliverymen.CHIKAO;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.healthworker.HealthworkerNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code HealthworkerFindCommandTest}.
 */
public class HealthworkerFindCommandTest {
    // TODO: Add healthworker into Model Manager after merge
    private Model model = new ModelManager(getTypicalOrderBook(),
            getTypicalUsersList(), getTypicalDeliverymenList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalOrderBook(),
            getTypicalUsersList(), getTypicalDeliverymenList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        HealthworkerNameContainsKeywordsPredicate firstPredicate =
                new HealthworkerNameContainsKeywordsPredicate("first");
        HealthworkerNameContainsKeywordsPredicate secondPredicate =
                new HealthworkerNameContainsKeywordsPredicate("second");

        HealthWorkerFindCommand findFirstOrderCommand = new HealthWorkerFindCommand(firstPredicate);
        HealthWorkerFindCommand findSecondOrderCommand = new HealthWorkerFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstOrderCommand.equals(findFirstOrderCommand));

        // same values -> returns true
        HealthWorkerFindCommand findFirstOrderCommandCopy = new HealthWorkerFindCommand(firstPredicate);
        assertTrue(findFirstOrderCommand.equals(findFirstOrderCommandCopy));

        // different types -> returns false
        assertFalse(findFirstOrderCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstOrderCommand.equals(null));

        // different common -> returns false
        assertFalse(findFirstOrderCommand.equals(findSecondOrderCommand));
    }

    @Test
    public void execute_invalidKeyword_noDeliverymanFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERYMEN_LISTED_OVERVIEW, 0);

        HealthworkerNameContainsKeywordsPredicate predicate = new HealthworkerNameContainsKeywordsPredicate("invalid");
        HealthWorkerFindCommand commandName = new HealthWorkerFindCommand(predicate);
        expectedModel.updateFilteredDeliverymenList(predicate);
        assertCommandSuccess(commandName, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDeliverymenList());
    }

    @Test
    public void execute_exactKeyword_oneDeliverymanFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERYMEN_LISTED_OVERVIEW, 1);
        HealthworkerNameContainsKeywordsPredicate predicate = new HealthworkerNameContainsKeywordsPredicate("Chi Kao");
        HealthWorkerFindCommand command = new HealthWorkerFindCommand(predicate);
        expectedModel.updateFilteredDeliverymenList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CHIKAO), model.getFilteredDeliverymenList());
    }

    @Test
    public void execute_partialKeyword_oneDeliverymanFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERYMEN_LISTED_OVERVIEW, 1);
        HealthworkerNameContainsKeywordsPredicate predicate = new HealthworkerNameContainsKeywordsPredicate("Chi");
        HealthWorkerFindCommand command = new HealthWorkerFindCommand(predicate);
        expectedModel.updateFilteredDeliverymenList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CHIKAO), model.getFilteredDeliverymenList());
    }
}
