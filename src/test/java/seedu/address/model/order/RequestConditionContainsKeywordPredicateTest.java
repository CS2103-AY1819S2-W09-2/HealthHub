package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.RequestBuilder;

public class RequestConditionContainsKeywordPredicateTest {
    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        RequestConditionContainsKeywordPredicate firstPredicate =
            new RequestConditionContainsKeywordPredicate(firstPredicateKeyword);
        RequestConditionContainsKeywordPredicate secondPredicate =
            new RequestConditionContainsKeywordPredicate(secondPredicateKeyword);

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same values -> returns true
        RequestConditionContainsKeywordPredicate firstPredicateCopy =
            new RequestConditionContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // Different food -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_foodContainsKeywords_returnsTrue() {
        // One keyword
        RequestConditionContainsKeywordPredicate predicate =
            new RequestConditionContainsKeywordPredicate("fried");
        assertTrue(predicate.test(new RequestBuilder().withCondition("fried rice").build()));

        // Keyword match 2 word
        predicate = new RequestConditionContainsKeywordPredicate("rice");
        assertTrue(predicate.test(new RequestBuilder().withCondition("fried rice").build()));

        // Leading and Trailing whitespace
        predicate = new RequestConditionContainsKeywordPredicate("     rice      ");
        assertTrue(predicate.test(new RequestBuilder().withCondition("fried rice").build()));

        // Mixed-case keywords
        predicate = new RequestConditionContainsKeywordPredicate("fRiEd RiCE");
        assertTrue(predicate.test(new RequestBuilder().withCondition("Fried Rice").build()));
    }

    @Test
    public void test_foodDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        RequestConditionContainsKeywordPredicate predicate = new RequestConditionContainsKeywordPredicate("tea");
        assertFalse(predicate.test(new RequestBuilder().withCondition("fried rice").build()));

        // Match no food
        predicate = new RequestConditionContainsKeywordPredicate("tea");
        assertFalse(predicate.test(new RequestBuilder().withCondition("fried rice", "ice milo", "roti canai").build()));
    }
}
