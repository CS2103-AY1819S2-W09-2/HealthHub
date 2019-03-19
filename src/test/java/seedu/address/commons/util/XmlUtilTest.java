package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.RequestBook;
import seedu.address.model.healthworker.HealthworkerList;
import seedu.address.storage.XmlAdaptedCondition;
import seedu.address.storage.XmlAdaptedRequest;
import seedu.address.storage.XmlHealthHub;
import seedu.address.storage.XmlSerializableRequestBook;
import seedu.address.storage.healthworker.XmlAdaptedHealthworker;
import seedu.address.storage.healthworker.XmlSerializableHealthworkerList;
import seedu.address.testutil.DeliverymanBuilder;
import seedu.address.testutil.DeliverymenListBuilder;
import seedu.address.testutil.OrderBookBuilder;
import seedu.address.testutil.RequestBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validOrderBook.xml");
    private static final Path VALID_DELIVERYMEN_LIST_FILE = TEST_DATA_FOLDER.resolve("validDeliverymenList.xml");
    private static final Path MISSING_ORDER_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingOrderField.xml");
    private static final Path INVALID_ORDER_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidOrderField.xml");
    private static final Path INVALID_DELIVERYMAN_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidDeliverymanField.xml");
    private static final Path VALID_ORDER_FILE = TEST_DATA_FOLDER.resolve("validOrder.xml");
    private static final Path VALID_DELIVERYMAN_FILE = TEST_DATA_FOLDER.resolve("validDeliveryman.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempOrderBook.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_ID = "1650dbab-e584-4e7f-87dc-fcac93a2aea9";
    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_ADDRESS = "4th street, 612234";
    private static final String VALID_STATUS = "PENDING";
    private static final String VALID_DATE = "01-10-2018 10:00:00";
    private static final List<XmlAdaptedCondition> VALID_FOOD =
        Collections.singletonList(new XmlAdaptedCondition("milo"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, RequestBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, RequestBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, RequestBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        RequestBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE,
            XmlSerializableRequestBookWithRootElement.class).toModelType();
        assertEquals(9, dataFromFile.getRequestList().size());
        HealthworkerList deliverymenDataFromFile = XmlUtil.getDataFromFile(VALID_DELIVERYMEN_LIST_FILE,
            XmlSerializableHealthworkerListWithRootElement.class).toModelType();
        assertEquals(2, deliverymenDataFromFile.getDeliverymenList().size());
    }

    @Test
    public void xmlAdaptedOrderFromFile_fileWithMissingOrderField_validResult() throws Exception {
        XmlAdaptedRequest actualOrder = XmlUtil.getDataFromFile(
            MISSING_ORDER_FIELD_FILE, XmlAdaptedRequestWithRootElement.class);
        XmlAdaptedRequest expectedOrder = new XmlAdaptedRequest(
            VALID_ID, null, VALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_FOOD, null);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void xmlAdaptedOrderFromFile_fileWithInvalidOrderField_validResult() throws Exception {
        XmlAdaptedRequest actualOrder = XmlUtil.getDataFromFile(
            INVALID_ORDER_FIELD_FILE, XmlAdaptedRequestWithRootElement.class);
        XmlAdaptedRequest expectedOrder = new XmlAdaptedRequest(
            VALID_ID, VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_FOOD, null);
        assertEquals(expectedOrder, actualOrder);

        XmlAdaptedHealthworker actualDeliveryman = XmlUtil.getDataFromFile(
            INVALID_DELIVERYMAN_FIELD_FILE, XmlAdaptedHealthworkerWithRootElement.class);
        XmlAdaptedHealthworker expectedDeliveryman = new XmlAdaptedHealthworker(VALID_NAME);
        assertNotEquals(expectedDeliveryman, actualDeliveryman);
    }

    @Test
    public void xmlAdaptedOrderFromFile_fileWithValidOrder_validResult() throws Exception {
        XmlAdaptedRequest actualOrder = XmlUtil.getDataFromFile(
            VALID_ORDER_FILE, XmlAdaptedRequestWithRootElement.class);
        XmlAdaptedRequest expectedOrder = new XmlAdaptedRequest(
            VALID_ID, VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_FOOD, null);

        System.out.println(actualOrder.toString());
        System.out.println(expectedOrder.toString());

        assertEquals(expectedOrder, actualOrder);

        XmlAdaptedHealthworker actualDeliveryman = XmlUtil.getDataFromFile(
            VALID_DELIVERYMAN_FILE, XmlAdaptedHealthworkerWithRootElement.class);
        XmlAdaptedHealthworker expectedDeliveryman = new XmlAdaptedHealthworker(VALID_ID, VALID_NAME, null);
        assertEquals(expectedDeliveryman, actualDeliveryman);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new RequestBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new RequestBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlHealthHub dataToWrite = new XmlHealthHub(new RequestBook(), new HealthworkerList());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlHealthHub dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE,
            XmlHealthHub.class);
        assertEquals(new RequestBook(), dataFromFile.getOrderBook());
        assertEquals(new HealthworkerList(), dataFromFile.getDeliverymenList());

        OrderBookBuilder builder = new OrderBookBuilder(new RequestBook()).withOrder(new RequestBuilder().build());
        DeliverymenListBuilder dBuilder = new DeliverymenListBuilder(new HealthworkerList())
            .withDeliveryman(new DeliverymanBuilder().build());
        dataToWrite = new XmlHealthHub(builder.build(), dBuilder.build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlHealthHub.class);
        assertEquals(dataToWrite.getOrderBook(), dataFromFile.getOrderBook());
        assertEquals(dataToWrite.getDeliverymenList(), dataFromFile.getDeliverymenList());

    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedRequest}
     * objects.
     */
    @XmlRootElement(name = "request")
    private static class XmlAdaptedRequestWithRootElement extends XmlAdaptedRequest {
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
     * {@code XmlAdaptedHealthworker} objects.
     */
    @XmlRootElement(name = "healthworker")
    private static class XmlAdaptedHealthworkerWithRootElement extends XmlAdaptedHealthworker {
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
     * {@code XmlAdaptedHealthworker} objects.
     */
    @XmlRootElement(name = "deliverymenlist")
    private static class XmlSerializableHealthworkerListWithRootElement extends XmlSerializableHealthworkerList {
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
     * {@code XmlAdaptedHealthworker} objects.
     */
    @XmlRootElement(name = "orderbook")
    private static class XmlSerializableRequestBookWithRootElement extends XmlSerializableRequestBook {
    }
}
