package seedu.address.storage;

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
import seedu.address.model.RequestBook;
import seedu.address.testutil.TypicalOrders;

public class XmlSerializableRequestBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableRequestBookTest");
    private static final Path TYPICAL_ORDERS_FILE = TEST_DATA_FOLDER.resolve("typicalOrdersOrderBook.xml");
    private static final Path INVALID_ORDER_FILE = TEST_DATA_FOLDER.resolve("invalidOrderOrderBook.xml");
    private static final Path DUPLICATE_ORDER_FILE = TEST_DATA_FOLDER.resolve("duplicateOrderOrderBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalOrdersFile_success() throws Exception {
        XmlSerializableRequestBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ORDERS_FILE,
            XmlSerializableRequestBookWithRootElement.class);
        RequestBook orderBookFromFile = dataFromFile.toModelType();
        RequestBook typicalOrdersOrderBook = TypicalOrders.getTypicalOrderBook();
        assertEquals(orderBookFromFile, typicalOrdersOrderBook);
        assertTrue(Streams.zip(orderBookFromFile.getRequestList().stream(),
            typicalOrdersOrderBook.getRequestList().stream(), (a, b) -> a.hasSameTag(b)).allMatch(x -> x));
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableRequestBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ORDER_FILE,
            XmlSerializableRequestBookWithRootElement.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableRequestBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ORDER_FILE,
            XmlSerializableRequestBookWithRootElement.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRequestBook.MESSAGE_DUPLICATE_ORDER);
        dataFromFile.toModelType();
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
     * {@code XmlAdaptedHealthworker} objects.
     */
    @XmlRootElement(name = "orderbook")
    private static class XmlSerializableRequestBookWithRootElement extends XmlSerializableRequestBook {
    }
}
