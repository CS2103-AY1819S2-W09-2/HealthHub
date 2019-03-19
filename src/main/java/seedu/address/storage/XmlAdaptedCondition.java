package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Condition;

/**
 * JAXB-friendly adapted version of the Condition.
 */
public class XmlAdaptedCondition {

    @XmlValue
    private String condition;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCondition() {
    }

    /**
     * Constructs a {@code XmlAdaptedCondition} with the given {@code condition}.
     */
    public XmlAdaptedCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Converts a given Condition into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedCondition(Condition source) {
        condition = source.foodName;
    }

    /**
     * Converts this jaxb-friendly adapted food object into the model's Condition object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted request
     */
    public Condition toModelType() throws IllegalValueException {
        if (!Condition.isValidFood(condition)) {
            throw new IllegalValueException(Condition.MESSAGE_FOOD_CONSTRAINTS);
        }
        return new Condition(condition);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCondition)) {
            return false;
        }

        return condition.equals(((XmlAdaptedCondition) other).condition);
    }

    @Override
    public String toString() {
        return this.condition;
    }
}
