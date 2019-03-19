package seedu.address.model.request;

import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Name} matches any of the keywords given.
 */
public class RequestNameContainsKeywordPredicate implements Predicate<Request> {
    private final String keyword;

    public RequestNameContainsKeywordPredicate(String name) {
        keyword = name.trim().toLowerCase();
    }

    @Override
    public boolean test(Request request) {
        return request.getName().fullName.toLowerCase().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RequestNameContainsKeywordPredicate // instanceof handles nulls
            && keyword.equals(((RequestNameContainsKeywordPredicate) other).keyword)); // state check
    }
}
