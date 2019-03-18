package seedu.address.logic.parser.healthworker;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.healthworker.FindHealthWorkerCommandParser.MESSAGE_EMPTY_NAME_FIELD;

import org.junit.Test;

import seedu.address.logic.commands.healthworker.HealthWorkerFindCommand;
import seedu.address.model.deliveryman.HealthworkerNameContainsKeywordsPredicate;

public class FindHealthWorkerCommandParserTest {

    private FindHealthWorkerCommandParser parser = new FindHealthWorkerCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HealthWorkerFindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "find ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HealthWorkerFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgValues_throwsParseException() {
        assertParseFailure(parser, " n/",
                String.format(MESSAGE_EMPTY_NAME_FIELD));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        HealthWorkerFindCommand expectedFindCommand =
                new HealthWorkerFindCommand(new HealthworkerNameContainsKeywordsPredicate("Alex"));
        assertParseSuccess(parser, " n/Alex", expectedFindCommand);
    }
}
