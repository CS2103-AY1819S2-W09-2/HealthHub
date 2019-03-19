package seedu.address.logic.commands.healthworker;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToHealthWorkerListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.healthworker.Healthworker;

/**
 * The Command that selects a healthworker identified using its displayed index
 */
public class HealthWorkerSelectCommand extends HealthWorkerCommand {
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = HealthWorkerCommand.COMMAND_WORD + " " + COMMAND_WORD
        + ": Selects the healthworker identified by the index number used in the displayed healthworker list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + HealthWorkerCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ORDER_SUCCESS = "Selected Healthworker: %1$s";

    private final Index targetIndex;

    public HealthWorkerSelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Healthworker> filteredHealthworkerList = model.getFilteredDeliverymenList();

        if (targetIndex.getZeroBased() >= filteredHealthworkerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToHealthWorkerListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_ORDER_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof HealthWorkerSelectCommand // instanceof handles nulls
            && targetIndex.equals(((HealthWorkerSelectCommand) other).targetIndex)); // state check
    }
}
