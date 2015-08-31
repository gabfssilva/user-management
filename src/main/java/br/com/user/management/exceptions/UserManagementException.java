package br.com.user.management.exceptions;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class UserManagementException extends RuntimeException {
    public UserManagementException() {
    }

    public UserManagementException(String message) {
        super(message);
    }

    public UserManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserManagementException(Throwable cause) {
        super(cause);
    }

    public UserManagementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
