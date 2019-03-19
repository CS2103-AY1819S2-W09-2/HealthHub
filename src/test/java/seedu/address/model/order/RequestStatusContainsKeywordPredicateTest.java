package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.RequestBuilder;

public class RequestStatusContainsKeywordPredicateTest {
    @Test
    public void equals() {
        List<RequestStatus> firstPredicateKeyword = Collections.singletonList(new RequestStatus("PENDING"));
        List<RequestStatus> secondPredicateKeyword =
                Arrays.asList(new RequestStatus("PENDING"), new RequestStatus("ONGOING"));

        RequestStatusContainsKeywordPredicate firstPredicate =
                new RequestStatusContainsKeywordPredicate(firstPredicateKeyword);
        RequestStatusContainsKeywordPredicate secondPredicate =
                new RequestStatusContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RequestStatusContainsKeywordPredicate firstPredicateCopy =
                new RequestStatusContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different status -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_statusContainsKeywords_returnsTrue() {
        // One keyword
        RequestStatusContainsKeywordPredicate predicate =
                new RequestStatusContainsKeywordPredicate(Collections.singletonList(new RequestStatus("PENDING")));
        assertTrue(predicate.test(new RequestBuilder().withStatus("PENDING").build()));

        // Keyword match only 2nd status
        predicate = new RequestStatusContainsKeywordPredicate(
                Arrays.asList(new RequestStatus("PENDING"), new RequestStatus("ONGOING")));
        assertTrue(predicate.test(new RequestBuilder().withStatus("ONGOING").build()));
    }

    @Test
    public void test_statusDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        RequestStatusContainsKeywordPredicate predicate =
                new RequestStatusContainsKeywordPredicate(Arrays.asList(new RequestStatus("PENDING")));
        assertFalse(predicate.test(new RequestBuilder().withStatus("COMPLETED").build()));
    }
}
