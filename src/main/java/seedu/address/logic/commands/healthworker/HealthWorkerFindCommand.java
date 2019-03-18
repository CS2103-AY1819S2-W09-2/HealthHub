package seedu.address.logic.commands.healthworker;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deliveryman.HealthworkerNameContainsKeywordsPredicate;

/**
 * Finds and lists all healthworker in healthworker list whose name contains in the argument keywords.
 * Keyword matching is case insensitive.
 */
public class HealthWorkerFindCommand extends HealthWorkerCommand {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = HealthWorkerCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Finds the healthworker whose name contains any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/KEYWORD \n"
            + "Example: " + HealthWorkerCommand.COMMAND_WORD + " " + COMMAND_WORD + " n/Alex";

    private final HealthworkerNameContainsKeywordsPredicate predicate;

    public HealthWorkerFindCommand(HealthworkerNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredDeliverymenList(predicate);
        return new CommandResult(
               String.format(Messages.MESSAGE_DELIVERYMEN_LISTED_OVERVIEW, model.getFilteredDeliverymenList().size())
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HealthWorkerFindCommand // instanceof handles nulls
                && predicate.equals(((HealthWorkerFindCommand) other).predicate)); // state check
    }
}
