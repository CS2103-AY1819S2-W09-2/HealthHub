package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all orders in the request book to the user.
 */
public class ListCommand extends RequestCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all orders";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
