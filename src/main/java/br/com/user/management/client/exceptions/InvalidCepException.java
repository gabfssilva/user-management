package br.com.user.management.client.exceptions;

import br.com.user.management.exceptions.UserManagementException;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class InvalidCepException extends UserManagementException {
    public InvalidCepException(String message) {
        super(message);
    }

    public InvalidCepException(String message, Throwable cause) {
        super(message, cause);
    }

}
