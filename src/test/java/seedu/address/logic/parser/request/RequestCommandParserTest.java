package seedu.address.logic.parser.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.request.AddCommand;
import seedu.address.logic.commands.request.ClearCommand;
import seedu.address.logic.commands.request.DeleteCommand;
import seedu.address.logic.commands.request.EditCommand;
import seedu.address.logic.commands.request.EditCommand.EditOrderDescriptor;
import seedu.address.logic.commands.request.FindCommand;
import seedu.address.logic.commands.request.ListCommand;
import seedu.address.logic.commands.request.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Request;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.RequestBuilder;
import seedu.address.testutil.OrderUtil;


public class RequestCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RequestCommandParser parser = new RequestCommandParser();

    @Test
    public void parse_add() throws Exception {
        Request request = new RequestBuilder().build();
        AddCommand command = (AddCommand) parser.parse(OrderUtil.getAddCommand(request));
        assertEquals(new AddCommand(request), command);
    }

    @Test
    public void parse_clear() throws Exception {
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parse_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parse(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parse_list() throws Exception {
        assertTrue(parser.parse(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parse(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parse_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parse(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST), command);
    }

    @Test
    public void parse_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String searchKeyword = keywords.stream().collect(Collectors.joining(" "));

        FindCommand command = (FindCommand) parser.parse(
                FindCommand.COMMAND_WORD + " n/" + searchKeyword);
        assertEquals(new FindCommand(new OrderNameContainsKeywordPredicate(searchKeyword)), command);
    }

    @Test
    public void parse_edit() throws Exception {
        Request request = new RequestBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(request).build();
        EditCommand command = (EditCommand) parser.parse(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + OrderUtil.getEditOrderDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_ORDER_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
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
