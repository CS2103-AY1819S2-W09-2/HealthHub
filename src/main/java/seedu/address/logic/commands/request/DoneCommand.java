package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.healthworker.Healthworker;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestStatus;

/**
 * Mark an request as COMPLETED.
 */
public class DoneCommand extends RequestCommand {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = RequestCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Marks an request as COMPLETED \n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + RequestCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 ";


    public static final String MESSAGE_COMPLETED_ORDER_SUCCESS = "Request %1$s have been completed.";
    public static final String MESSAGE_ONGOING_ORDER = "Only ONGOING status can be marked as completed.";
    public static final String MESSAGE_DELIVERYMAN_NOT_EXIST = "Healthworker does not exist inside healthworker list.";

    private final Index targetIndex;

    public DoneCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Request> lastShownList = model.getFilteredOrderList();
        List<Healthworker> lastShowHealthworkerList = model.getFilteredDeliverymenList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Request requestToBeCompleted = lastShownList.get(targetIndex.getZeroBased());

        if (!requestToBeCompleted.getRequestStatus().equals(new RequestStatus("ONGOING"))) {
            throw new CommandException(MESSAGE_ONGOING_ORDER);
        }



        //fetch healthworker from index because request's healthworker not reliable.
        Healthworker healthworkerToRemoveOrder = requestToBeCompleted.getHealthworker();
        Healthworker correctHealthworker = lastShowHealthworkerList.stream()
                .filter(deliveryman -> deliveryman.equals(healthworkerToRemoveOrder))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_DELIVERYMAN_NOT_EXIST));
        Healthworker updatedHealthworker = removeOrderFromDeliveryman(correctHealthworker, requestToBeCompleted);

        requestToBeCompleted.setStatusCompleted();

        model.updateOrder(requestToBeCompleted, requestToBeCompleted);
        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);
        model.commitOrderBook();

        model.updateDeliveryman(correctHealthworker, updatedHealthworker);
        model.updateFilteredDeliverymenList(Model.PREDICATE_SHOW_ALL_DELIVERYMEN);
        model.commitDeliverymenList();

        return new CommandResult(String.format(MESSAGE_COMPLETED_ORDER_SUCCESS, requestToBeCompleted));
    }

    /**
     * Remove request from healthworker.
     */
    private static Healthworker removeOrderFromDeliveryman(Healthworker targetHealthworker, Request targetRequest) {
        assert targetHealthworker != null;
        assert targetRequest != null;

        Healthworker removedOrderHealthworker = new Healthworker(targetHealthworker);
        removedOrderHealthworker.removeOrder(targetRequest);

        return removedOrderHealthworker;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneCommand // instanceof handles nulls
                && targetIndex.equals(((DoneCommand) other).targetIndex)); // state check
    }
}
