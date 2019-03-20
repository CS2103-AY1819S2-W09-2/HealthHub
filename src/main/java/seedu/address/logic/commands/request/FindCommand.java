package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.request.Request;


/**
 * Finds and lists all orders in orders list whose name or phone contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends RequestCommand {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = RequestCommand.COMMAND_WORD + " " + COMMAND_WORD
        + ": Finds the request whose keywords contain any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: [n/NAME] [p/PHONE] [a/ADDRESS] [dt/DATETIME] [c/CONDITION] " +
        "[st/ORDER_STATUS]\n"
        + "Example: " + RequestCommand.COMMAND_WORD + " " + COMMAND_WORD + " n/John " +
        "c/Physiotherapy\n"
        + "Example: " + RequestCommand.COMMAND_WORD + " " + COMMAND_WORD
        + " dt/01-10-2018 10:00:00 dt/03-10-2018 10:00:00";

    private final Predicate<Request> predicate;

    public FindCommand(Predicate<Request> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredOrderList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_REQUESTS_LISTED_OVERVIEW, model.getFilteredOrderList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
