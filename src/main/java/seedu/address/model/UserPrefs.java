package seedu.address.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path foodZoomFilePath = Paths.get("data", "addressbook.xml");
    private Path routeListFilePath = Paths.get("data", "routelist.xml");
    private Path usersListFilePath = Paths.get("data", "users.xml");

    private Path deliverymenListFilePath = Paths.get("data", "deliverymen.xml");

    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public Path getHealthHubFilePath() {
        return foodZoomFilePath;
    }

    public void setFoodZoomFilePath(Path foodZoomFilePath) {
        this.foodZoomFilePath = foodZoomFilePath;
    }

    public Path getRouteListFilePath() {
        return routeListFilePath;
    }

    public void setRouteListFilePath(Path routeListFilePath) {
        this.routeListFilePath = routeListFilePath;
    }

    public Path getUsersListFilePath() {
        return usersListFilePath;
    }

    public void setUsersListFilePath(Path usersListFilePath) {
        this.usersListFilePath = usersListFilePath;
    }

    public Path getDeliverymenListFilePath() {
        return deliverymenListFilePath;
    }

    public void setDeliverymenListFilePath(Path deliverymenListFilePath) {
        this.deliverymenListFilePath = deliverymenListFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
            && Objects.equals(foodZoomFilePath, o.foodZoomFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, foodZoomFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + foodZoomFilePath);
        sb.append("\nLocal data file location : " + usersListFilePath);
        return sb.toString();
    }

}
