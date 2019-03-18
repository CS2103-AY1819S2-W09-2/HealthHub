package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DELIVERYMAN_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.healthworker.AddHealthWorkerCommand;
import seedu.address.logic.commands.healthworker.HealthWorkerCommand;
import seedu.address.logic.commands.healthworker.HealthWorkerDeleteCommand;
import seedu.address.logic.commands.healthworker.HealthWorkerFindCommand;
import seedu.address.logic.commands.healthworker.HealthWorkerListCommand;
import seedu.address.logic.commands.healthworker.HealthWorkerSelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses healthworker input.
 */
public class HealthWorkerCommandParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    /**
     * Parses the given {@code String} of arguments in the context of the HealthWorkerCommand
     * and returns an HealthWorkerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public HealthWorkerCommand parse (String args) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_DELIVERYMAN_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddHealthWorkerCommand.COMMAND_WORD:
            return new AddHealthWorkerCommandParser().parse(arguments);

        case HealthWorkerSelectCommand.COMMAND_WORD:
            return new DeliverymanSelectCommandParser().parse(arguments);

        case HealthWorkerDeleteCommand.COMMAND_WORD:
            return new DeliverymanDeleteCommandParser().parse(arguments);

        case HealthWorkerListCommand.COMMAND_WORD:
            return new HealthWorkerListCommand();

        case HealthWorkerFindCommand.COMMAND_WORD:
            return new DeliverymanFindCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
