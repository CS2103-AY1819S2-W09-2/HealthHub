package seedu.address.logic.parser.request;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.RequestStatus;

public class RequestStatusPredicateUtilTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_multipleValidStringStatus_returnStatusList() throws ParseException {
        List<RequestStatus> expectedRequestStatuses = Arrays.asList(new RequestStatus("PENDING"), new RequestStatus("ONGOING"));

        List<String> stringStatuses = Arrays.asList("PENDING", "ONGOING");
        List<RequestStatus> statuses = new RequestStatusPredicateUtil().parseOrderStatusKeywords(stringStatuses);

        assertEquals(expectedRequestStatuses, statuses);
    }

    @Test
    public void test_mixedCaseStringStatuses_returnValidStatusList() throws ParseException {
        List<RequestStatus> expectedRequestStatuses = Arrays.asList(new RequestStatus("PENDING"));

        List<String> stringStatuses = Arrays.asList("pENdiNG");
        List<RequestStatus> statuses = new RequestStatusPredicateUtil().parseOrderStatusKeywords(stringStatuses);

        assertEquals(expectedRequestStatuses, statuses);
    }

    @Test
    public void test_invalidStatusGiven_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);

        List<String> stringStatuses = Arrays.asList("invalidStatus");
        List<RequestStatus> statuses = new RequestStatusPredicateUtil().parseOrderStatusKeywords(stringStatuses);
    }
}
