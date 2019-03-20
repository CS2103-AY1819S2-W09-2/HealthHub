package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.request.Request;

/**
 * Deletes an request identified using it's displayed index from the request book.
 */
public class DeleteCommand extends RequestCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = RequestCommand.COMMAND_WORD + " " + COMMAND_WORD
        + ": Deletes the request identified by the index number used in the displayed request list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + RequestCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Request: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Request> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
        }

        Request requestToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (requestToDelete.isAlreadyAssignedDeliveryman() && requestToDelete.isOngoing()) {
            throw new CommandException(Messages.MESSAGE_ORDER_HAS_DELIVERYMAN_CANNOT_DELETE);
        }

        model.deleteOrder(requestToDelete);
        model.commitOrderBook();
        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, requestToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
