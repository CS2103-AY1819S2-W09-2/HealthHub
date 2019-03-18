package seedu.address.logic.commands.healthworker;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deliveryman.Healthworker;

/**
 * Deletes a healthworker identified using it's displayed index from the address book.
 */
public class HealthWorkerDeleteCommand extends HealthWorkerCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = HealthWorkerCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the healthworker identified by the index number used in the displayed healthworker list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + HealthWorkerCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DELIVERYMAN_SUCCESS = "Deleted Healthworker: %1$s";

    private final Index targetIndex;

    public HealthWorkerDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Healthworker> lastShownList = model.getFilteredDeliverymenList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }

        Healthworker healthworkerToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (healthworkerToDelete.hasOrders()) {
            throw new CommandException(String.format(Messages.MESSAGE_DELIVERYMEN_HAS_ORDERS_CANNOT_DELETE,
                healthworkerToDelete));
        }
        model.deleteDeliveryman(healthworkerToDelete);
        model.commitDeliverymenList();
        return new CommandResult(String.format(MESSAGE_DELETE_DELIVERYMAN_SUCCESS, healthworkerToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HealthWorkerDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((HealthWorkerDeleteCommand) other).targetIndex)); // state check
    }
}

