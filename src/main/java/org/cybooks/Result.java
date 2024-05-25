package org.cybooks;

/**
 * Represents the result of an operation, typically used for database operations.
 * Contains a success status and an accompanying message.
 */
public class Result {
    private boolean success; // Indicates whether the operation was successful or not
    private String message; // A message providing additional information about the operation

    /**
     * Constructs a new Result object with the specified success status and message.
     *
     * @param success Indicates whether the operation was successful (true) or not (false)
     * @param message A message providing additional information about the operation
     */
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Retrieves the success status of the operation.
     *
     * @return true if the operation was successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Retrieves the message associated with the operation result.
     *
     * @return A message providing additional information about the operation
     */
    public String getMessage() {
        return message;
    }
}
