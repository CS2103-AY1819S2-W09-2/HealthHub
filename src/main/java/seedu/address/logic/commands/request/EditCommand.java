package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.deliveryman.Healthworker;
import seedu.address.model.order.Food;
import seedu.address.model.order.Request;
import seedu.address.model.order.RequestDate;
import seedu.address.model.order.RequestStatus;


/**
 * Edits an request to the request book
 */
public class EditCommand extends RequestCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = RequestCommand.COMMAND_WORD + " " + COMMAND_WORD
        + ": Edits the details of the request identified "
        + "by the index number used in the displayed request book. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_PHONE + "PHONE] "
        + "[" + PREFIX_ADDRESS + "ADDRESS] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_FOOD + "FOOD]...\n"
        + "Example: " + RequestCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_FOOD + "Roti Prata "
        + PREFIX_FOOD + "Ice Milo";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Request: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This request already exists in the request book.";
    public static final String MESSAGE_INVALID_ORDER_INDEX = "Invalid Request Index: %1$s";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param index               of the request in the filtered request list to edit
     * @param editOrderDescriptor details to edit the request with
     */
    public EditCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(index);
        requireNonNull(editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    /**
     * Creates and returns a {@code Request} with the details of {@code requestToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Request createEditedOrder(Request requestToEdit, EditOrderDescriptor editOrderDescriptor) {
        assert requestToEdit != null;

        Name updatedName = editOrderDescriptor.getName().orElse(requestToEdit.getName());
        Phone updatedPhone = editOrderDescriptor.getPhone().orElse(requestToEdit.getPhone());
        Address updatedAddress = editOrderDescriptor.getAddress().orElse(requestToEdit.getAddress());
        RequestDate updatedDate = editOrderDescriptor.getDate().orElse(requestToEdit.getDate());
        Set<Food> updatedFood = editOrderDescriptor.getFood().orElse(requestToEdit.getFood());
        RequestStatus requestStatus = requestToEdit.getRequestStatus();
        Healthworker healthworker = requestToEdit.getHealthworker();

        return new Request(updatedName, updatedPhone, updatedAddress, updatedDate, requestStatus, updatedFood,
            healthworker);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Request> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Request requestToEdit = lastShownList.get(index.getZeroBased());
        Request editedRequest = createEditedOrder(requestToEdit, editOrderDescriptor);


        if (!requestToEdit.isSameOrder(editedRequest) && model.hasOrder(editedRequest)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        if (editedRequest.isCompleted()) {
            throw new CommandException(Messages.MESSAGE_COMPLETED_ORDER);
        }

        if (editedRequest.isAlreadyAssignedDeliveryman()) {
            throw new CommandException(String.format(Messages.MESSAGE_ORDER_ALREADY_ASSIGNED_TO_DELIVERYMAN,
                index.getOneBased(), editedRequest.getHealthworker()));
        }

        model.updateOrder(requestToEdit, editedRequest);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        model.commitOrderBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ORDER_SUCCESS, editedRequest));

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
            && editOrderDescriptor.equals(e.editOrderDescriptor);
    }

    /**
     * Stores the details to edit the request with. Each non-empty field value will replace the
     * corresponding field value of the request.
     */
    public static class EditOrderDescriptor {
        private Name name;
        private Phone phone;
        private Address address;
        private RequestDate requestDate;
        private Set<Food> food;

        public EditOrderDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code food} is used internally.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setAddress(toCopy.address);
            setDate(toCopy.requestDate);
            setFood(toCopy.food);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, address, requestDate, food);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<RequestDate> getDate() {
            return Optional.ofNullable(requestDate);
        }

        public void setDate(RequestDate requestDate) {
            this.requestDate = requestDate;
        }

        /**
         * Returns an unmodifiable food set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Food>> getFood() {
            return (food != null) ? Optional.of(Collections.unmodifiableSet(food)) : Optional.empty();
        }

        /**
         * Sets {@code food} to this object's {@code food}.
         * A defensive copy of {@code food} is used internally.
         */
        public void setFood(Set<Food> food) {
            this.food = (food != null) ? new HashSet<>(food) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            // state check
            EditOrderDescriptor e = (EditOrderDescriptor) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getAddress().equals(e.getAddress())
                && getDate().equals(e.getDate())
                && getFood().equals(e.getFood());
        }
    }
}
