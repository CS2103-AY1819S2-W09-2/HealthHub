package seedu.address.model.deliveryman.exceptions;

/**
 * Indicated that the operation exceeds the request limit in healthworker.
 */
public class RequestLimitExceededException extends Exception {
    public RequestLimitExceededException() {
        super("Operations would result in more orders than limit.");
    }
}
