package br.com.user.management.chains.exceptions;

import br.com.user.management.exceptions.UserManagementException;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class AbsentUserException extends UserManagementException {
    public AbsentUserException(String message) {
        super(message);
    }
}
