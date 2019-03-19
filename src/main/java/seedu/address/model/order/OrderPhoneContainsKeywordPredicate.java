package seedu.address.model.order;

import java.util.List;

import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Phone} matches any of the keywords given.
 */
public class OrderPhoneContainsKeywordPredicate implements Predicate<Request> {
    private final List<String> keywords;

    public OrderPhoneContainsKeywordPredicate(List<String> phone) {
        keywords = phone;
    }

    @Override
    public boolean test(Request request) {
        return keywords.stream()
                .anyMatch(keyword -> request.getPhone().value.contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderPhoneContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((OrderPhoneContainsKeywordPredicate) other).keywords)); // state check
    }
}
