package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHWORKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUEST;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.healthworker.Healthworker;
import seedu.address.model.healthworker.exceptions.RequestLimitExceededException;
import seedu.address.model.request.Request;

/**
 * Assigns multiple orders to a healthworker with two way association.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "/assign";

    public static final String MESSAGE_SUCCESS = "Assigned request %1$s successfully to healthworker %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign orders to healthworker. "
        + "Parameters: "
        + PREFIX_HEALTHWORKER + "DELIVERYMAN_ID "
        + PREFIX_REQUEST + "ORDER_ID \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_HEALTHWORKER + "1 "
        + PREFIX_REQUEST + "1 " + PREFIX_REQUEST + "3";

    private final Index deliverymanId;
    private final Set<Index> orderIds;

    /**
     * Creates an AssignCommand to add the specified {@code user}
     */
    public AssignCommand(Index deliverymanId, Set<Index> orderIds) {
        requireAllNonNull(deliverymanId, orderIds);
        this.deliverymanId = deliverymanId;
        this.orderIds = orderIds;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Healthworker> lastShownHealthworkerList = model.getFilteredDeliverymenList();
        List<Request> lastShownRequestList = model.getFilteredOrderList();
        Set<Request> ordersToAdd = new HashSet<>();

        if (deliverymanId.getZeroBased() >= lastShownHealthworkerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }
        Healthworker healthworkerToAssign = lastShownHealthworkerList.get(deliverymanId.getZeroBased());

        for (Index i : orderIds) {
            if (i.getZeroBased() >= lastShownRequestList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
            }
            Request request = lastShownRequestList.get(i.getZeroBased());
            if (request.isCompleted()) {
                throw new CommandException(Messages.MESSAGE_COMPLETED_ORDER);
            }

            if (request.isAlreadyAssignedDeliveryman()) {
                throw new CommandException(String.format(Messages.MESSAGE_ORDER_ALREADY_ASSIGNED_TO_DELIVERYMAN,
                    i.getOneBased(), request.getHealthworker()));
            }
            ordersToAdd.add(request);
        }

        Healthworker assignedHealthworker = new Healthworker(healthworkerToAssign);
        if (!assignedHealthworker.canAccommodate(ordersToAdd)) {
            throw new CommandException(Messages.MESSAGE_ORDERS_LIMIT_EXCEEDED);
        }

        for (Request request : ordersToAdd) {
            Request updatedRequest = new Request(request);
            try {
                updatedRequest.setHealthworker(assignedHealthworker);
            } catch (RequestLimitExceededException e) {
                throw new CommandException(Messages.MESSAGE_ORDERS_LIMIT_EXCEEDED);
            }
            model.updateOrder(request, updatedRequest);
        }

        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);
        model.commitOrderBook();

        model.updateDeliveryman(healthworkerToAssign, assignedHealthworker);
        model.updateFilteredDeliverymenList(Model.PREDICATE_SHOW_ALL_DELIVERYMEN);
        model.commitDeliverymenList();

        StringJoiner validSj = new StringJoiner(", ");
        for (Index i : orderIds) {
            validSj.add(Integer.toString(i.getOneBased()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, validSj.toString(), assignedHealthworker));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AssignCommand // instanceof handles nulls
            && deliverymanId.equals(((AssignCommand) other).deliverymanId)
            && orderIds.equals(((AssignCommand) other).orderIds));
    }
}
