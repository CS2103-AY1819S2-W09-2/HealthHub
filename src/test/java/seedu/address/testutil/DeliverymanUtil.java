package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.healthworker.AddHealthWorkerCommand;
import seedu.address.model.healthworker.Healthworker;

/**
 * A utility class for Healthworker testing.
 */
public class DeliverymanUtil {

    /**
     * Returns an add command string for adding the {@code healthworker}.
     */
    public static String getDeliverymanAddCommand(Healthworker healthworker) {
        return AddHealthWorkerCommand.COMMAND_WORD + " " + getDeliverymanDetails(healthworker);
    }

    /**
     * Returns the part of the command string for the given {@code healthworker}'s details.
     */
    public static String getDeliverymanDetails(Healthworker healthworker) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + healthworker.getName().fullName + " ");
        return sb.toString();
    }
}
