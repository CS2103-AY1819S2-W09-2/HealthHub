package seedu.address.logic.parser.healthworker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DELIVERYMAN_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.healthworker.AddHealthWorkerCommand;
import seedu.address.logic.commands.healthworker.HealthWorkerDeleteCommand;
import seedu.address.logic.commands.healthworker.HealthWorkerFindCommand;
import seedu.address.logic.commands.healthworker.HealthWorkerListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.healthworker.Healthworker;
import seedu.address.model.healthworker.HealthworkerNameContainsKeywordsPredicate;
import seedu.address.testutil.DeliverymanBuilder;
import seedu.address.testutil.DeliverymanUtil;


public class HealthWorkerCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final HealthWorkerCommandParser parser = new HealthWorkerCommandParser();

    @Test
    public void parse_add() throws Exception {
        Healthworker healthworker = new DeliverymanBuilder().build();
        AddHealthWorkerCommand command = (AddHealthWorkerCommand)
            parser.parse(DeliverymanUtil.getDeliverymanAddCommand(healthworker));
        assertEquals(new AddHealthWorkerCommand(healthworker), command);
    }

    @Test
    public void parse_delete() throws Exception {
        HealthWorkerDeleteCommand command = (HealthWorkerDeleteCommand) parser.parse(
                HealthWorkerDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new HealthWorkerDeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parse_list() throws Exception {
        assertTrue(parser.parse(HealthWorkerListCommand.COMMAND_WORD) instanceof HealthWorkerListCommand);
        assertTrue(parser.parse(HealthWorkerListCommand.COMMAND_WORD + " 3") instanceof HealthWorkerListCommand);
    }

    @Test
    public void parse_find() throws Exception {
        String keyword = "foo";
        HealthWorkerFindCommand command = (HealthWorkerFindCommand) parser.parse(
                HealthWorkerFindCommand.COMMAND_WORD + " n/" + keyword);
        assertEquals(new HealthWorkerFindCommand(
                new HealthworkerNameContainsKeywordsPredicate(keyword)), command);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_DELIVERYMAN_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parse("");
        parser.parse("3");
    }

    @Test
    public void parse_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parse("unknownCommand");
    }
}
