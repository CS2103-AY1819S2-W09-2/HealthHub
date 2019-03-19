package seedu.address.model.order.exceptions;

/**
 * Signals that the operation will result in duplicate Request (Orders are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRequestException extends RuntimeException {
    public DuplicateRequestException() {
        super("Operation would result in duplicate orders");
    }
}
