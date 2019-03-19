package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_ORDERS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BENSON;
import static seedu.address.testutil.TypicalOrders.CARL;
import static seedu.address.testutil.TypicalOrders.DANIEL;
import static seedu.address.testutil.TypicalOrders.FIONA;
import static seedu.address.testutil.TypicalOrders.KEYWORD_NAME_MATCHING_MEIER;
import static seedu.address.testutil.TypicalOrders.KEYWORD_PHONE_MATCHING_BENSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.request.DeleteCommand;
import seedu.address.logic.commands.request.FindCommand;
import seedu.address.logic.commands.request.RequestCommand;
import seedu.address.model.Model;
import seedu.address.model.request.Condition;

public class FindCommandSystemTest extends RequestBookSystemTest {

    @Test
    public void find() {
        String findCommand = RequestCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD;

        /* Login */
        String loginCommand = LoginCommand.COMMAND_WORD + " ";
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
            + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
        executeCommand(command);
        setUpOrderListPanel();

        /* Case: find multiple persons in request book by name, command with leading spaces and trailing spaces
         * -> 2 orders found
         */
        command = "   " + findCommand + " " + KEYWORD_NAME_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where request list is displaying the persons we are finding
         * -> 2 request found
         */
        command = findCommand + " " + KEYWORD_NAME_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find single common in request book by phone, command with leading spaces and trailing spaces */
        command = "    " + findCommand + " " + KEYWORD_PHONE_MATCHING_BENSON + "   ";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where request list is displaying the persons we are finding */
        command = findCommand + " " + KEYWORD_PHONE_MATCHING_BENSON;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find common name where request list is not displaying the request we are finding -> 1 request found */
        command = findCommand + " " + PREFIX_NAME + "Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find common phone where request list is not displaying the request we are finding -> 1 request found */
        command = findCommand + " " + PREFIX_PHONE + "98765432";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find request by exact name in request book -> 1 request found */
        command = findCommand + " " + PREFIX_NAME + "Benson Meier";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find request exact name name in request book, in reversed request -> 1 request found */
        command = findCommand + " " + PREFIX_NAME + "Meier Benson";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find single request by phone in request book, with leading and trailing whitespace
         * -> 1 request found
         */
        command = findCommand + " " + PREFIX_PHONE + "  98765432  ";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find single request by phone in request book, single space in between.
         * -> 1 request found
         */
        command = findCommand + " " + PREFIX_PHONE + "9876 5432";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find single request by phone in request book, spaces in between.
         * -> 1 request found
         */
        command = findCommand + " " + PREFIX_PHONE + "9876    5432";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find same orders in request book after deleting 1 of them -> 1 request found */
        executeCommand(RequestCommand.COMMAND_WORD + " " + DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getOrderBook().getRequestList().contains(BENSON));
        command = findCommand + " " + KEYWORD_NAME_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name in request book, keyword is same as name but of different case -> 1 request found */
        command = findCommand + " " + PREFIX_NAME + "MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name in request book, keyword is substring of name -> 0 orders found */
        command = findCommand + " " + PREFIX_NAME + "Mei";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name in request book, name is substring of keyword -> 0 orders found */
        command = findCommand + " " + PREFIX_NAME + "Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name not in request book -> 0 orders found */
        command = findCommand + " " + PREFIX_NAME + "Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find condition of request in request book -> 1 orders found */
        List<Condition> condition = new ArrayList<>(DANIEL.getCondition());
        command = findCommand + " " + PREFIX_CONDITION + condition.get(0).foodName;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of request in request book -> 1 request found */
        command = findCommand + " " + PREFIX_ADDRESS + DANIEL.getAddress().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find status of request in request book -> 1 request found */
        command = findCommand + " " + PREFIX_STATUS + "COMPLETED";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find single date of request in request book -> 1 request found */
        command = findCommand + " " + PREFIX_DATE + "04-10-2018 10:00:00";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone in request book, keyword is substring of phone -> 1 request found */
        command = findCommand + " " + PREFIX_PHONE + "8765";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find date range of orders in request book -> 3 orders found */
        command = findCommand + " " + PREFIX_DATE + "01-10-2018 10:00:00" + " " + PREFIX_DATE + "02-10-2018 10:00:00";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find with phone and address in request book -> 1 request found */
        command = findCommand + " " + PREFIX_PHONE + "8765" + " " + PREFIX_ADDRESS + "10th street";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find 2 different statuses of request in request book -> 1 request found */
        command = findCommand + " " + PREFIX_STATUS + "ONGOING COMPLETED";
        ModelHelper.setFilteredList(expectedModel, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone not in request book -> 0 orders found */
        command = findCommand + " " + PREFIX_PHONE + "4243587470";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find by name while an request is selected -> selected card deselected */
        showAllOrders();
        selectOrder(Index.fromOneBased(1));
        assertFalse(getOrderListPanel().getHandleToSelectedCard().getConditions().equals(DANIEL.getCondition()));
        command = findCommand + " " + PREFIX_NAME + "Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find request by name in empty request book -> 0 orders found */
        deleteAllOrders();
        command = findCommand + " " + KEYWORD_NAME_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "/request FiNd " + PREFIX_NAME + "Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_ORDERS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code RequestBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see RequestBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
            MESSAGE_ORDERS_LISTED_OVERVIEW, expectedModel.getFilteredOrderList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RequestBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see RequestBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
