package seedu.address.logic.parser.request;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.request.RequestStatus;

/**
 * Parses of status keywords and returns a valid status list
 */
public class RequestStatusPredicateUtil {

    /**
     * Parses a list of {@code stringStatus} and returns a list of RequestStatus object
     * @throws ParseException if invalid status is supplied
     */
    public List<RequestStatus> parseOrderStatusKeywords(List<String> stringStatuses) throws ParseException {
        List<RequestStatus> statuses = new ArrayList<>();

        for (String stringStatus : stringStatuses) {
            String upperCaseStringStatus = stringStatus.toUpperCase();

            if (!RequestStatus.isValidStatus(upperCaseStringStatus)) {
                throw new ParseException(RequestStatus.MESSAGE_STATUS_CONSTRAINTS);
            }

            statuses.add(new RequestStatus(upperCaseStringStatus));
        }

        return statuses;
    }
}
