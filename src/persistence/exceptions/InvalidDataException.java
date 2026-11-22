package persistence.exceptions;

/**
 * Represents an error when the data is invalid.
 */
public class InvalidDataException extends Exception {

  /**
   * Creates an exception with an error message.
   *
   * @param message the error message
   */
  public InvalidDataException(String message) {
    super(message);
  }

  /**
   * Creates an exception with an error message and a cause.
   *
   * @param message the error message
   * @param cause the original error
   */
  public InvalidDataException(String message, Throwable cause) {
    super(message, cause);
  }
}