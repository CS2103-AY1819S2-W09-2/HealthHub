package seedu.address.model.order;

import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Address} matches any of the keywords given.
 */
public class OrderAddressContainsKeywordPredicate implements Predicate<Request> {
    private final String keyword;

    public OrderAddressContainsKeywordPredicate(String address) {
        keyword = address.trim().toLowerCase();
    }

    @Override
    public boolean test(Request request) {
        return request.getAddress().value.toLowerCase().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderAddressContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((OrderAddressContainsKeywordPredicate) other).keyword)); // state check
    }

}
