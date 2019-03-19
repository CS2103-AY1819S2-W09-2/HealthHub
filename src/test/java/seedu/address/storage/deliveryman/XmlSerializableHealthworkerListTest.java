package seedu.address.storage.deliveryman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Streams;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.deliveryman.HealthworkerList;
import seedu.address.testutil.TypicalDeliverymen;

public class XmlSerializableHealthworkerListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
        "XmlSerializableHealthworkerListTest");
    private static final Path TYPICAL_DELIVERYMEN_FILE =
            TEST_DATA_FOLDER.resolve("typicalDeliverymenDeliverymenList.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidDeliverymanDeliverymenList.xml");
    private static final Path DUPLICATE_PERSON_FILE =
            TEST_DATA_FOLDER.resolve("duplicateDeliverymenDeliverymenList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalDeliverymenFile_success() throws Exception {
        XmlSerializableHealthworkerList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_DELIVERYMEN_FILE,
            XmlSerializableHealthworkerListWithRootElement.class);
        HealthworkerList healthworkerListFromFile = dataFromFile.toModelType();
        HealthworkerList typicalDeliverymenHealthworkerList = TypicalDeliverymen.getTypicalDeliverymenList();
        assertEquals(typicalDeliverymenHealthworkerList.getDeliverymenList().get(1),
            healthworkerListFromFile.getDeliverymenList().get(1));
        assertEquals(healthworkerListFromFile, typicalDeliverymenHealthworkerList);
        assertTrue(Streams.zip(healthworkerListFromFile.getDeliverymenList().stream(),
            typicalDeliverymenHealthworkerList.getDeliverymenList().stream(), (a, b) -> a.hasSameTag(b))
            .allMatch(x -> x));
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableHealthworkerList dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
            XmlSerializableHealthworkerListWithRootElement.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateDeliverymen_throwsIllegalValueException() throws Exception {
        XmlSerializableHealthworkerList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
            XmlSerializableHealthworkerListWithRootElement.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableHealthworkerList.MESSAGE_DUPLICATE_DELIVERYMAN);
        dataFromFile.toModelType();
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
     * {@code XmlAdaptedHealthworker} objects.
     */
    @XmlRootElement(name = "deliverymenlist")
    private static class XmlSerializableHealthworkerListWithRootElement extends XmlSerializableHealthworkerList {}

}

