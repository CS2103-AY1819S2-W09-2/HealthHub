package seedu.address.storage.deliveryman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.deliveryman.XmlAdaptedHealthworker.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalDeliverymen.RAJUL;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Name;
import seedu.address.testutil.Assert;

public class XmlAdaptedHealthworkerTest {
    private static final String INVALID_NAME = "D@mi+h";

    @Test
    public void toModelType_validDeliverymanDetails_returnsDeliveryman() throws Exception {
        XmlAdaptedHealthworker deliveryman = new XmlAdaptedHealthworker(RAJUL);
        assertEquals(RAJUL, deliveryman.toModelType());
        assertTrue(RAJUL.hasSameTag(deliveryman.toModelType()));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedHealthworker deliveryman =
            new XmlAdaptedHealthworker(INVALID_NAME);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, deliveryman::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedHealthworker deliveryman = new XmlAdaptedHealthworker((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, deliveryman::toModelType);
    }

}
