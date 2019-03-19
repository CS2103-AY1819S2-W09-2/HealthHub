package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import seedu.address.testutil.RequestBuilder;

public class RequestDateSinglePredicateTest {
    @Test
    public void equals() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date firstPredicateKeyword = null;
        Date secondPredicateKeyword = null;

        try {
            firstPredicateKeyword = sf.parse("01-10-2018 10:00:00");
            secondPredicateKeyword = sf.parse("03-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        RequestDateSinglePredicate firstPredicate =
            new RequestDateSinglePredicate(firstPredicateKeyword);
        RequestDateSinglePredicate secondPredicate =
            new RequestDateSinglePredicate(secondPredicateKeyword);

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same values -> returns true
        RequestDateSinglePredicate firstPredicateCopy =
            new RequestDateSinglePredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // Different date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateContainsKeywords_returnsTrue() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date datePredicateKeyword = null;

        try {
            datePredicateKeyword = sf.parse("01-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        // Exact match
        RequestDateSinglePredicate predicate = new RequestDateSinglePredicate(datePredicateKeyword);
        assertTrue(predicate.test(new RequestBuilder().withDate("01-10-2018 10:00:00").build()));
    }

    @Test
    public void test_dateDoesNotContainKeywords_returnsFalse() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date datePredicateKeyword = null;

        try {
            datePredicateKeyword = sf.parse("01-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        // Different date, same time
        RequestDateSinglePredicate predicate = new RequestDateSinglePredicate(datePredicateKeyword);
        assertFalse(predicate.test(new RequestBuilder().withDate("03-10-2018 10:00:00").build()));

        // Same date, different time
        assertFalse(predicate.test(new RequestBuilder().withDate("01-10-2018 13:00:00").build()));
    }
}
