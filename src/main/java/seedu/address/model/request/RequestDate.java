package seedu.address.model.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an Request's date in the request book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)} (String)}
 */
public class RequestDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be in the format dd-MM-yyyy HH:mm:ss and it should be a valid date.";

    private static final SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private Date date = null;

    /**
     * Constructs a {@code RequestDate}.
     *
     * @param orderDate A valid Request Date.
     */
    public RequestDate(String orderDate) {

        requireNonNull(orderDate);

        try {
            sf.setLenient(false);
            date = sf.parse(orderDate);
        } catch (ParseException pE) {
            checkArgument(false, MESSAGE_DATE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String orderDate) {
        try {
            sf.setLenient(false);
            sf.parse(orderDate);
            return true;
        } catch (ParseException pE) {
            return false;
        }
    }

    public Date getDate() {
        return date;
    }

    public Date getShortenedDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
            return dateFormat.parse(dateFormat.format(date));
        } catch (ParseException pE) {
            return date;
        }
    }

    @Override
    public String toString() {
        return sf.format(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestDate // instanceof handles nulls
                && date.equals(((RequestDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
