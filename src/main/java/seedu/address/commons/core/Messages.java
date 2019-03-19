package seedu.address.commons.core;

import static seedu.address.model.deliveryman.Healthworker.ORDERS_LIMIT;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ROUTE_COMMAND_FORMAT = "Invalid route command format! \n%1$s";
    public static final String MESSAGE_INVALID_ORDER_COMMAND_FORMAT = "Invalid request command format! \n%1$s";
    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The request index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_ORDERS_LISTED_OVERVIEW = "%1$d orders listed!";
    public static final String MESSAGE_COMPLETED_ORDER = "This request is already delivered to the customer.";
    public static final String MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX =
        "The healthworker index provided is invalid";
    public static final String MESSAGE_INVALID_DELIVERYMAN_COMMAND_FORMAT =
        "Invalid healthworker command format! \n%1$s";
    public static final String MESSAGE_DELIVERYMEN_LISTED_OVERVIEW = "%1$d deliverymen listed!";
    public static final String MESSAGE_DELIVERYMEN_HAS_ORDERS_CANNOT_DELETE = "Unable to delete healthworker %1$s, "
            + "currently assigned to some orders.";
    public static final String MESSAGE_ORDER_HAS_DELIVERYMAN_CANNOT_DELETE = "Unable to delete request, "
            + "already assigned to a healthworker.";
    public static final String MESSAGE_ORDER_ALREADY_ASSIGNED_TO_DELIVERYMAN = "Request %1$s is already assigned to "
            + "healthworker %2$s, cannot be edited or reassigned to another healthworker!";
    public static final String MESSAGE_ORDER_ONGOING_CANNOT_CLEAR = "There is at least one ongoing request"
        + " in the list, request list cannot be cleared.";
    public static final String MESSAGE_REQUIRE_LOGIN = "Please login first!";
    public static final String MESSAGE_ORDERS_LIMIT_EXCEEDED = String.format(
        "You cannot assign more than %1$d orders to a healthworker.", ORDERS_LIMIT);
}
