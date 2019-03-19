package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.request.EditCommand.EditOrderDescriptor;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.order.Condition;
import seedu.address.model.order.Request;
import seedu.address.model.order.RequestDate;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class EditOrderDescriptorBuilder {
    private EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        descriptor = new EditOrderDescriptor();
    }

    public EditOrderDescriptorBuilder(EditOrderDescriptor descriptor) {
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOrderDescriptor} with fields containing {@code request}'s details
     */
    public EditOrderDescriptorBuilder(Request request) {
        descriptor = new EditOrderDescriptor();
        descriptor.setName(request.getName());
        descriptor.setPhone(request.getPhone());
        descriptor.setAddress(request.getAddress());
        descriptor.setDate(request.getDate());
        descriptor.setCondition(request.getCondition());
    }

    /**
     * Sets the {@code Name} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withDate(String date) {
        descriptor.setDate(new RequestDate(date));
        return this;
    }

    /**
     * Parses the {@code food} into a {@code Set<Condition>} and set it to the {@code EditOrderDescriptor}
     * that we are building.
     */
    public EditOrderDescriptorBuilder withFood(String... food) {
        Set<Condition> conditionSet = Stream.of(food).map(Condition::new).collect(Collectors.toSet());
        descriptor.setCondition(conditionSet);
        return this;
    }

    public EditOrderDescriptor build() {
        return descriptor;
    }
}
