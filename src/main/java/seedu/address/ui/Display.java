package seedu.address.ui;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BackToHomeEvent;
import seedu.address.commons.events.ui.HealthWorkerPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RequestPanelSelectionChangedEvent;
import seedu.address.model.request.Condition;
import seedu.address.model.request.Request;
import seedu.address.ui.display.DeliverymanDisplayCard;
import seedu.address.ui.display.OrderDisplayCard;


/**
 * Panel containing the main display - map and side content.
 */
public class Display extends UiPart<Region> {

    private static final String FXML = "Display.fxml";
    private final Logger logger = LogsCenter.getLogger(Display.class);

    @FXML
    private StackPane mapWrapper;

    @FXML
    private StackPane displayPanelPlaceholder;

    @FXML
    private StackPane statisticsWrapper;

    private StatisticsPanel statisticsPanel;
    private TreeMap<Date, Integer> orderHistory;
    private HashMap<String, Integer> conditionHistory;
    private double progress;
    private int total;

    private MapPanel mapPanel;
    private ObservableList<Request> requestList;
    private HashMap<String, Integer> directory;


    /**
     * Constructor for this panel. Process information related to request and updates respective UI components
     *
     * @param requestList the current list of orders in-memory
     */
    public Display(ObservableList<Request> requestList) {
        super(FXML);
        this.requestList = requestList;
        this.progress = 0;
        this.total = requestList.size();

        fillInnerParts();
        setupMap();
        setupStatistics();

        this.requestList.addListener((ListChangeListener.Change<? extends Request> change) -> {
            while (change.next()) {

                if (change.wasUpdated()) {

                    this.progress = 0;
                    this.total = 0;

                    directory = new HashMap<>();
                    updateMapCache(change.getList());

                    orderHistory = new TreeMap<>();
                    updateOrderHistory(change.getList());

                    conditionHistory = new HashMap<>();
                    addConditions(change.getList());

                    trackProgress(change.getList(), false);
                    total = change.getList().size();

                } else {
                    removeFromMapCache(change.getRemoved());
                    updateMapCache(change.getAddedSubList());

                    removeFromOrderHistory(change.getRemoved());
                    updateOrderHistory(change.getAddedSubList());

                    removeConditions(change.getRemoved());
                    addConditions(change.getAddedSubList());

                    trackProgress(change.getRemoved(), true);
                    trackProgress(change.getAddedSubList(), false);

                    total += change.getAddedSubList().size();
                    total -= change.getRemoved().size();
                }

                mapPanel.clear();
                mapPanel.initialise(directory);

                statisticsPanel.clear();
                statisticsPanel.initialize(orderHistory);
                statisticsPanel.updateLabels(total, progress / total, getCommonConditions());
            }
        });

        registerAsAnEventHandler(this);
    }

    /**
     * Initializes and sets up the variables needed for map component
     */
    private void setupMap() {
        directory = new HashMap<>();
        updateMapCache(requestList);
        mapPanel.initialise(directory);
    }

    /**
     * Initializes and sets up the variables needed for statistics component
     */
    private void setupStatistics() {
        orderHistory = new TreeMap<>();
        conditionHistory = new HashMap<>();
        this.progress = 0;

        logger.info(progress + "   " + total);

        addConditions(requestList);
        trackProgress(requestList, false);
        updateOrderHistory(requestList);

        statisticsPanel.initialize(orderHistory);

        logger.info(progress + "   " + total);

        statisticsPanel.updateLabels(total, progress / total, getCommonConditions());
    }

    /**
     * Fills the panel with the map and statistics
     */
    private void fillInnerParts() {
        mapPanel = new MapPanel();
        mapWrapper.getChildren().add(mapPanel.getRoot());

        statisticsPanel = new StatisticsPanel();
        statisticsWrapper.getChildren().add(statisticsPanel.getRoot());
    }

    /**
     * This updates the progress bar in the statistics panel with % of pending orders
     *
     * @param changeList list of orders that have to be changed
     * @param toRemove   a flag to indicate whether to update or remove
     */
    public void trackProgress(List<? extends Request> changeList, boolean toRemove) {
        for (Request o : changeList) {
            if (o.getRequestStatus().toString().equals("PENDING")) {
                if (toRemove) {
                    progress--;
                } else {
                    progress++;
                }
            }
        }
    }

    /**
     * Looks through hashmap to find the most popular food item ordered
     *
     * @return a String that represents the food item ordered the most
     */
    private String getCommonConditions() {
        String commonCondition = "";
        int bestVal = -1;

        for (String cond : conditionHistory.keySet()) {
            if (conditionHistory.get(cond) > bestVal) {
                bestVal = conditionHistory.get(cond);
                commonCondition = cond;
            }
        }

        return commonCondition;
    }

    /**
     * Adds food items onto the purchase history hashmap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void addConditions(List<? extends Request> changeList) {
        for (Request o : changeList) {
            Set<Condition> conditionList = o.getCondition();
            for (Condition item : conditionList) {
                String conditionKey = item.toString();
                if (conditionHistory.containsKey(conditionKey)) {
                    conditionHistory.put(conditionKey, conditionHistory.get(conditionKey) + 1);
                } else {
                    conditionHistory.put(conditionKey, 1);
                }
            }
        }
    }

    /**
     * Removes food items from the purchase history hashmap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void removeConditions(List<? extends Request> changeList) {
        for (Request o : changeList) {
            Set<Condition> conditionList = o.getCondition();
            for (Condition item : conditionList) {
                String conditionKey = item.toString();
                if (conditionHistory.containsKey(conditionKey)) {
                    if (conditionHistory.get(conditionKey) <= 1) {
                        conditionHistory.remove(conditionKey);
                    } else {
                        conditionHistory.put(conditionKey, conditionHistory.get(conditionKey) - 1);
                    }
                }
            }
        }
    }

    /**
     * Removes addresses from the directory hashmap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void removeFromMapCache(List<? extends Request> changeList) {
        for (Request o : changeList) {
            if (o.getRequestStatus().toString().equals("PENDING")) {
                String postalCode = o.getAddress().getPostalCode();
                String postalCodeKey = postalCode.substring(0, 2);
                if (directory.containsKey(postalCodeKey)) {
                    if (directory.get(postalCodeKey) <= 1) {
                        directory.remove(postalCodeKey);
                    } else {
                        directory.put(postalCodeKey, directory.get(postalCodeKey) - 1);
                    }
                }
            }
        }
    }

    /**
     * Adds addresses onto the directory hashmap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void updateMapCache(List<? extends Request> changeList) {
        for (Request o : changeList) {
            if (o.getRequestStatus().toString().equals("PENDING")) {
                String postalCode = o.getAddress().getPostalCode();
                String postalCodeKey = postalCode.substring(0, 2);
                if (directory.containsKey(postalCodeKey)) {
                    directory.put(postalCodeKey, directory.get(postalCodeKey) + 1);
                } else {
                    directory.put(postalCodeKey, 1);
                }
            }
        }
    }

    /**
     * Adds orders onto the orderHistory treemap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void updateOrderHistory(List<? extends Request> changeList) {
        for (Request o : changeList) {
            Date dateKey = o.getDate().getShortenedDate();
            logger.info("ADDED " + o.toString());
            if (orderHistory.containsKey(dateKey)) {
                orderHistory.put(dateKey, orderHistory.get(dateKey) + 1);
            } else {
                orderHistory.put(dateKey, 1);
            }
        }

        logger.info(orderHistory.toString());
    }

    /**
     * Removes orders from the orderHistory treemap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void removeFromOrderHistory(List<? extends Request> changeList) {
        for (Request o : changeList) {
            Date dateKey = o.getDate().getShortenedDate();
            logger.info("REMOVED " + o.toString());
            if (orderHistory.containsKey(dateKey)) {
                if (orderHistory.get(dateKey) <= 1) {
                    orderHistory.remove(dateKey);
                } else {
                    orderHistory.put(dateKey, orderHistory.get(dateKey) - 1);
                }
            }
        }

        logger.info(orderHistory.toString());
    }

    @Subscribe
    public void handleBackToHomeRequest(BackToHomeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        statisticsPanel = new StatisticsPanel();
        displayPanelPlaceholder.getChildren().setAll(statisticsPanel.getRoot());

        setupStatistics();
    }

    @Subscribe
    public void handleOrderPanelSelectionChangedEvent(RequestPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayPanelPlaceholder.getChildren().setAll(new OrderDisplayCard(event.getNewSelection()).getRoot());
    }

    @Subscribe
    public void handleDeliveryPanelSelectionChangedEvent(HealthWorkerPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayPanelPlaceholder.getChildren().setAll(new DeliverymanDisplayCard(event.getNewSelection()).getRoot());
    }
}
