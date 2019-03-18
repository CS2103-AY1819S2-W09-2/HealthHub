package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.healthworker.HealthWorkerSelectCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HealthWorkerSelectCommand object.
 */
public class DeliverymanSelectCommandParser implements Parser<HealthWorkerSelectCommand> {
    /**
     * Parses the fiven {@code string} of srgumants in the context of the HealthWorkerSelectCommand
     * and returns a HealthWorkerSelectCommand object for execution.
     */
    public HealthWorkerSelectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new HealthWorkerSelectCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HealthWorkerSelectCommand.MESSAGE_USAGE), pe);
        }
    }
}
