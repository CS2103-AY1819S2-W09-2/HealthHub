package seedu.address.model.healthworker;

import java.util.function.Predicate;

/**
 * Tests that a {@code Healthworker}'s {@code Name} matches any of the keywords given.
 */
public class HealthworkerNameContainsKeywordsPredicate implements Predicate<Healthworker> {
    private final String keyword;

    public HealthworkerNameContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword.trim().toLowerCase();
    }

    @Override
    public boolean test(Healthworker healthworker) {
        return healthworker.getName().fullName.toLowerCase().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HealthworkerNameContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((HealthworkerNameContainsKeywordsPredicate) other).keyword)); // state check
    }
}
