package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedRequest.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalOrders.BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.order.Condition;
import seedu.address.model.order.RequestDate;
import seedu.address.testutil.Assert;

public class XmlAdaptedRequestTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DATE = "12/10/2018";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_DATE = BENSON.getDate().toString();
    private static final String VALID_STATUS = BENSON.getRequestStatus().toString();
    private static final List<XmlAdaptedCondition> VALID_FOOD = BENSON.getCondition().stream()
        .map(XmlAdaptedCondition::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        XmlAdaptedRequest order = new XmlAdaptedRequest(BENSON);
        assertEquals(BENSON, order.toModelType());
        assertTrue(BENSON.hasSameTag(order.toModelType()));
    }

    @Test
    public void toModelType_invalidOrder_throwsIllegalValueException() {
        XmlAdaptedRequest order = new XmlAdaptedRequest(INVALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_STATUS,
            VALID_FOOD, null);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedRequest order = new XmlAdaptedRequest(null, VALID_PHONE, VALID_ADDRESS, VALID_DATE,
            VALID_STATUS, VALID_FOOD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedRequest order = new XmlAdaptedRequest(VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_DATE,
            VALID_STATUS, VALID_FOOD, null);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedRequest order = new XmlAdaptedRequest(
            VALID_NAME, null, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_FOOD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }


    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedRequest order = new XmlAdaptedRequest(VALID_NAME, VALID_PHONE, INVALID_ADDRESS, VALID_DATE,
            VALID_STATUS, VALID_FOOD, null);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedRequest order = new XmlAdaptedRequest(
            VALID_NAME, VALID_PHONE, null, VALID_DATE, VALID_STATUS, VALID_FOOD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedRequest order = new XmlAdaptedRequest(VALID_NAME, VALID_PHONE, VALID_ADDRESS,
            INVALID_DATE, VALID_STATUS, VALID_FOOD, null);
        String expectedMessage = RequestDate.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedRequest order = new XmlAdaptedRequest(VALID_NAME, VALID_PHONE, VALID_ADDRESS, null,
            VALID_STATUS, VALID_FOOD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullFood_throwsIllegalValueException() {
        XmlAdaptedRequest order = new XmlAdaptedRequest(VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_STATUS,
            null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Condition.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }
}
