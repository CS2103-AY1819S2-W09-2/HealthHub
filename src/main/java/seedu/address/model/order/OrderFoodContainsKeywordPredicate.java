package seedu.address.model.order;

import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Food} matches any of the keywords given.
 */
public class OrderFoodContainsKeywordPredicate implements Predicate<Request> {
    private final String keyword;

    public OrderFoodContainsKeywordPredicate(String food) {
        keyword = food.trim().toLowerCase();
    }

    @Override
    public boolean test(Request request) {
        return request.getFood().stream()
                .anyMatch(orderFood -> orderFood.foodName.toLowerCase().contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderFoodContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((OrderFoodContainsKeywordPredicate) other).keyword)); // state check
    }
}
