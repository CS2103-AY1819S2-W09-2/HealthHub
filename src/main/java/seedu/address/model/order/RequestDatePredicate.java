package seedu.address.model.order;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Date} matches any of the keywords given.
 */
public class RequestDatePredicate implements Predicate<Request> {
    public static final int VALID_SEARCH_DATE_RANGE_SIZE = 2;
    public static final int LOWER_DATE_RANGE_INDEX = 0;
    public static final int UPPER_DATE_RANGE_INDEX = 1;

    private Predicate<Request> datePredicate;

    public RequestDatePredicate(List<Date> dates) {
        assert dates.size() < 3;

        if (dates.size() == VALID_SEARCH_DATE_RANGE_SIZE) {
            Date lowerDateBoundary = dates.get(LOWER_DATE_RANGE_INDEX);
            Date upperDateBoundary = dates.get(UPPER_DATE_RANGE_INDEX);
            assert (lowerDateBoundary.before(upperDateBoundary) && lowerDateBoundary != upperDateBoundary);
            datePredicate = new RequestDateRangePredicate(lowerDateBoundary, upperDateBoundary);
        } else {
            // One date in list
            datePredicate = new RequestDateSinglePredicate(dates.get(0));
        }
    }

    @Override
    public boolean test(Request request) {
        return datePredicate.test(request);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RequestDatePredicate // instanceof handles nulls
            && datePredicate.equals(((RequestDatePredicate) other).datePredicate)); // state check
    }
}
