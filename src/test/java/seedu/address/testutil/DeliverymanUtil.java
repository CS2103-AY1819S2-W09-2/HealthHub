package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.healthworker.AddHealthWorkerCommand;
import seedu.address.model.deliveryman.Deliveryman;

/**
 * A utility class for Deliveryman testing.
 */
public class DeliverymanUtil {

    /**
     * Returns an add command string for adding the {@code healthworker}.
     */
    public static String getDeliverymanAddCommand(Deliveryman deliveryman) {
        return AddHealthWorkerCommand.COMMAND_WORD + " " + getDeliverymanDetails(deliveryman);
    }

    /**
     * Returns the part of the command string for the given {@code healthworker}'s details.
     */
    public static String getDeliverymanDetails(Deliveryman deliveryman) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + deliveryman.getName().fullName + " ");
        return sb.toString();
    }
}
