package seedu.address.model.request;

import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Condition} matches any of the keywords given.
 */
public class RequestConditionContainsKeywordPredicate implements Predicate<Request> {
    private final String keyword;

    public RequestConditionContainsKeywordPredicate(String food) {
        keyword = food.trim().toLowerCase();
    }

    @Override
    public boolean test(Request request) {
        return request.getCondition().stream()
                .anyMatch(orderFood -> orderFood.foodName.toLowerCase().contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestConditionContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((RequestConditionContainsKeywordPredicate) other).keyword)); // state check
    }
}
