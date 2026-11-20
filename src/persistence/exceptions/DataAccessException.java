package persistence.exceptions;

/**
 * Represents an error when reading or saving data.
 */
public class DataAccessException extends Exception {

    /**
     * Creates a data access exception with a message and a cause.
     *
     * @param message the error message
     * @param cause the original error
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}