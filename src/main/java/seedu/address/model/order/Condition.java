package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Condition's name in the request book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFoodName(String)}
 */
public class Condition {

    public static final String MESSAGE_FOOD_CONSTRAINTS =
            "Condition should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the food must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String FOOD_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String foodName;

    /**
     * Constructs a {@code Condition}.
     *
     * @param name A valid name.
     */
    public Condition(String name) {
        requireNonNull(name);
        checkArgument(isValidFood(name), MESSAGE_FOOD_CONSTRAINTS);
        foodName = name;
    }

    /**
     * Returns true if a given string is a valid Condition.
     */
    public static boolean isValidFood(String test) {
        return test.matches(FOOD_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return foodName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Condition // instanceof handles nulls
                && foodName.equals(((Condition) other).foodName)); // state check
    }

    @Override
    public int hashCode() {
        return foodName.hashCode();
    }

}
