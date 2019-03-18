package seedu.address.logic.commands.healthworker;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deliveryman.Deliveryman;

/**
 * Represents the command to add a healthworker.
 */
public class AddHealthWorkerCommand extends HealthWorkerCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = HealthWorkerCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds healthworker to the list. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + "Example: " + HealthWorkerCommand.COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_NAME + "Lorem Ipsum";

    public static final String MESSAGE_SUCCESS = "New healthworker added: %1$s";
    public static final String MESSAGE_DUPLICATE_DELIVERYMAN = "This healthworker already exists in the list.";

    private final Deliveryman toAdd;

    /**
     * Creates an AddHealthWorkerCommand to add the specified {@code Deliverman}
     */
    public AddHealthWorkerCommand(Deliveryman dman) {
        requireNonNull(dman);
        toAdd = dman;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasDeliveryman(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DELIVERYMAN);
        }

        model.addDeliveryman(toAdd);
        model.commitDeliverymenList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddHealthWorkerCommand
                && toAdd.isSameDeliveryman(((AddHealthWorkerCommand) other).toAdd));
    }
}
