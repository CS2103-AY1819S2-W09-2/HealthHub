package seedu.address.model.request;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.RequestBuilder;

public class RequestNameContainsKeywordPredicateTest {
    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        RequestNameContainsKeywordPredicate firstPredicate =
            new RequestNameContainsKeywordPredicate(firstPredicateKeyword);
        RequestNameContainsKeywordPredicate secondPredicate =
            new RequestNameContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RequestNameContainsKeywordPredicate firstPredicateCopy =
            new RequestNameContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different name -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        RequestNameContainsKeywordPredicate predicate =
            new RequestNameContainsKeywordPredicate("Alice");
        assertTrue(predicate.test(new RequestBuilder().withName("Alice Bob").build()));

        // Keyword match 2 word
        predicate = new RequestNameContainsKeywordPredicate("Bob");
        assertTrue(predicate.test(new RequestBuilder().withName("Alice Bob").build()));

        // Partial Match
        predicate = new RequestNameContainsKeywordPredicate("Ali");
        assertTrue(predicate.test(new RequestBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new RequestNameContainsKeywordPredicate("aLIce bOB");
        assertTrue(predicate.test(new RequestBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        RequestNameContainsKeywordPredicate predicate = new RequestNameContainsKeywordPredicate("Carol");
        assertFalse(predicate.test(new RequestBuilder().withName("Alice Bob").build()));
    }
}
