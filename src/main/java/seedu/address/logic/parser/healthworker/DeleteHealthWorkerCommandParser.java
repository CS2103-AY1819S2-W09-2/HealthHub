package seedu.address.logic.parser.healthworker;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.healthworker.HealthWorkerDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input arguments to produce a HealthWorkerDeleteCommand.
 */
public class DeleteHealthWorkerCommandParser implements Parser<HealthWorkerDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments and returns a HealthWorkerDeleteCommand
     * object for execution.
     * @throws ParseException if the input does not conform to the expected format
     */
    public HealthWorkerDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new HealthWorkerDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HealthWorkerDeleteCommand.MESSAGE_USAGE), pe
            );
        }
    }
}
