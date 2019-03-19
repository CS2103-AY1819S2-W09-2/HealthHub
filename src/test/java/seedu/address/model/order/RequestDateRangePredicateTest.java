package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import seedu.address.testutil.RequestBuilder;

public class RequestDateRangePredicateTest {
    @Test
    public void equals() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date firstPredicateFirstKeyword = null;
        Date firstPredicateSecondKeyword = null;
        Date secondPredicateFirstKeyword = null;
        Date secondPredicateSecondKeyword = null;

        try {
            firstPredicateFirstKeyword = sf.parse("01-10-2018 10:00:00");
            firstPredicateSecondKeyword = sf.parse("03-10-2018 10:00:00");
            secondPredicateFirstKeyword = sf.parse("01-10-2018 12:00:00");
            secondPredicateSecondKeyword = sf.parse("03-10-2018 12:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }


        RequestDateRangePredicate firstPredicate =
                new RequestDateRangePredicate(firstPredicateFirstKeyword, firstPredicateSecondKeyword);
        RequestDateRangePredicate secondPredicate =
                new RequestDateRangePredicate(secondPredicateFirstKeyword, secondPredicateSecondKeyword);

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same values -> returns true
        RequestDateRangePredicate firstPredicateCopy =
                new RequestDateRangePredicate(firstPredicateFirstKeyword, firstPredicateSecondKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // Different date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_datesWithinRange_returnsTrue() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date lowerDateBoundary = null;
        Date upperDateBoundary = null;

        try {
            lowerDateBoundary = sf.parse("01-10-2018 10:00:00");
            upperDateBoundary = sf.parse("03-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        // Equals lower boundary
        RequestDateRangePredicate predicate = new RequestDateRangePredicate(lowerDateBoundary, upperDateBoundary);
        assertTrue(predicate.test(new RequestBuilder().withDate("01-10-2018 10:00:00").build()));

        // Equals upper boundary
        assertTrue(predicate.test(new RequestBuilder().withDate("03-10-2018 10:00:00").build()));

        // Within boundary
        assertTrue(predicate.test(new RequestBuilder().withDate("02-10-2018 10:00:00").build()));
    }

    @Test
    public void test_dateOutsideBoundary_returnsFalse() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date lowerDateBoundary = null;
        Date upperDateBoundary = null;

        try {
            lowerDateBoundary = sf.parse("01-10-2018 10:00:00");
            upperDateBoundary = sf.parse("03-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        // Below lower boundary
        RequestDateRangePredicate predicate = new RequestDateRangePredicate(lowerDateBoundary, upperDateBoundary);
        assertFalse(predicate.test(new RequestBuilder().withDate("01-10-2018 09:00:59").build()));

        // Exceed upper boundary
        assertFalse(predicate.test(new RequestBuilder().withDate("03-10-2018 10:00:01").build()));
    }
}
