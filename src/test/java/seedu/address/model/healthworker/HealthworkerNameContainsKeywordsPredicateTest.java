package seedu.address.model.healthworker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.DeliverymanBuilder;

public class HealthworkerNameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        HealthworkerNameContainsKeywordsPredicate firstPredicate =
                new HealthworkerNameContainsKeywordsPredicate(firstPredicateKeyword);
        HealthworkerNameContainsKeywordsPredicate secondPredicate =
                new HealthworkerNameContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        HealthworkerNameContainsKeywordsPredicate firstPredicateCopy =
                new HealthworkerNameContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different healthworker -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        HealthworkerNameContainsKeywordsPredicate predicate =
                new HealthworkerNameContainsKeywordsPredicate("Alice");
        assertTrue(predicate.test(new DeliverymanBuilder().withName("Alice Bob").build()));

        // Keyword match 2 word
        predicate = new HealthworkerNameContainsKeywordsPredicate("Bob");
        assertTrue(predicate.test(new DeliverymanBuilder().withName("Alice Bob").build()));

        // Partial Match
        predicate = new HealthworkerNameContainsKeywordsPredicate("Ali");
        assertTrue(predicate.test(new DeliverymanBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new HealthworkerNameContainsKeywordsPredicate("aLIce bOB");
        assertTrue(predicate.test(new DeliverymanBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        HealthworkerNameContainsKeywordsPredicate predicate = new HealthworkerNameContainsKeywordsPredicate("Carol");
        assertFalse(predicate.test(new DeliverymanBuilder().withName("Alice Bob").build()));
    }
}
