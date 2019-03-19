package seedu.address.model.request;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RequestDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new RequestDate(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDate = "12/10/18";
        Assert.assertThrows(IllegalArgumentException.class, () -> new RequestDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> RequestDate.isValidDate(null));

        // invalid date
        assertFalse(RequestDate.isValidDate("")); // empty string
        assertFalse(RequestDate.isValidDate(" ")); // spaces only
        assertFalse(RequestDate.isValidDate("12-13-2018")); // date without time
        assertFalse(RequestDate.isValidDate("12/13/2018")); // wrong date format
        assertFalse(RequestDate.isValidDate("12-13 10:00:00")); // wrong date format
        assertFalse(RequestDate.isValidDate("2018-12-10 10:00")); // wrong date format
        assertFalse(RequestDate.isValidDate("31-04-2018 01:00:00")); // invalid date value
        assertFalse(RequestDate.isValidDate("29-02-2018 00:00:00")); // invalid date value

        // valid date
        assertTrue(RequestDate.isValidDate("10-10-18 10:00:00"));
        assertTrue(RequestDate.isValidDate("09-04-2017 12:00:00"));
        assertTrue(RequestDate.isValidDate("06-11-2018 06:00:00"));
        assertTrue(RequestDate.isValidDate("29-02-2016 00:00:00"));
    }
}
