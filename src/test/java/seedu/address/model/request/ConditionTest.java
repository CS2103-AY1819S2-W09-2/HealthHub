package seedu.address.model.request;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ConditionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Condition(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(invalidName));
    }

    @Test
    public void isValidFood() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Condition.isValidFood(null));

        // invalid food
        assertFalse(Condition.isValidFood("")); // empty string
        assertFalse(Condition.isValidFood(" ")); // spaces only
        assertFalse(Condition.isValidFood("^")); // only non-alphanumeric characters
        assertFalse(Condition.isValidFood("peter*")); // contains non-alphanumeric characters

        // valid food
        assertTrue(Condition.isValidFood("roti prata")); // alphabets only
        assertTrue(Condition.isValidFood("chocolate milkshake")); // numbers only
        assertTrue(Condition.isValidFood("ice tea")); // alphanumeric characters
        assertTrue(Condition.isValidFood("rojak")); // with capital letters
        assertTrue(Condition.isValidFood("lasagna")); // long names
    }
}
