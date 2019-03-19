package seedu.address.model.order;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Status} matches the keywords given.
 */
public class OrderStatusContainsKeywordPredicate implements Predicate<Request> {
    private final List<OrderStatus> keywords;

    public OrderStatusContainsKeywordPredicate(List<OrderStatus> statuses) {
        keywords = statuses;
    }

    @Override
    public boolean test(Request request) {
        return keywords.stream()
                .anyMatch(keyword -> request.getOrderStatus().equals(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderStatusContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((OrderStatusContainsKeywordPredicate) other).keywords)); // state check
    }
}
