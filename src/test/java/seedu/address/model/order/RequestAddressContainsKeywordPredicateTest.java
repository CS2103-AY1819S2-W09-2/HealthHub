package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.RequestBuilder;

public class RequestAddressContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "151011";
        String secondPredicateKeyword = "132133";

        RequestAddressContainsKeywordPredicate firstPredicate =
                new RequestAddressContainsKeywordPredicate(firstPredicateKeyword);
        RequestAddressContainsKeywordPredicate secondPredicate =
                new RequestAddressContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RequestAddressContainsKeywordPredicate firstPredicateCopy =
                new RequestAddressContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // Exact match
        RequestAddressContainsKeywordPredicate predicate =
                new RequestAddressContainsKeywordPredicate("Block 312, Amy Street 1, 612234");
        assertTrue(predicate.test(new RequestBuilder().withAddress("Block 312, Amy Street 1, 612234").build()));

        // Partial match
        predicate = new RequestAddressContainsKeywordPredicate("Block 312");
        assertTrue(predicate.test(new RequestBuilder().withAddress("Block 312, Amy Street 1, 612234").build()));

        // Leading and trailing whitespaces
        predicate = new RequestAddressContainsKeywordPredicate("    Block 312, Amy Street 1    ");
        assertTrue(predicate.test(new RequestBuilder().withAddress("Block 312, Amy Street 1, 612234").build()));

        // Mixed-case keywords
        predicate = new RequestAddressContainsKeywordPredicate("bLoCk 31");
        assertTrue(predicate.test(new RequestBuilder().withAddress("Block 31, 612234").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        RequestAddressContainsKeywordPredicate predicate =
                new RequestAddressContainsKeywordPredicate("Block 123, Bobby Street 3, 612234");
        assertFalse(predicate.test(new RequestBuilder().withAddress("Block 312, Amy Street 1, 612234").build()));
    }
}
