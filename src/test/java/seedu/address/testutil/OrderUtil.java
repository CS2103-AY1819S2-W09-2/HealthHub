package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;

import seedu.address.logic.commands.request.AddCommand;
import seedu.address.logic.commands.request.EditCommand.EditOrderDescriptor;
import seedu.address.model.request.Condition;
import seedu.address.model.request.Request;


/**
 * A utility class for Request.
 */
public class OrderUtil {

    /**
     * Returns an add command string for adding the {@code request}.
     */
    public static String getAddCommand(Request request) {
        return AddCommand.COMMAND_WORD + " " + getOrderDetails(request);
    }

    /**
     * Returns the part of command string for the given {@code request}'s details.
     */
    public static String getOrderDetails(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + request.getName().fullName + " ");
        sb.append(PREFIX_PHONE + request.getPhone().value + " ");
        sb.append(PREFIX_ADDRESS + request.getAddress().value + " ");
        sb.append(PREFIX_DATE + request.getDate().toString() + " ");
        request.getCondition().stream().forEach(
            s -> sb.append(PREFIX_CONDITION + s.foodName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditOrderDescriptor}'s details.
     */
    public static String getEditOrderDescriptorDetails(EditOrderDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date).append(" "));
        if (descriptor.getCondition().isPresent()) {
            Set<Condition> condition = descriptor.getCondition().get();
            if (condition.isEmpty()) {
                sb.append(PREFIX_CONDITION);
            } else {
                condition.forEach(s -> sb.append(PREFIX_CONDITION).append(s.foodName).append(" "));
            }
        }
        return sb.toString();
    }

}
