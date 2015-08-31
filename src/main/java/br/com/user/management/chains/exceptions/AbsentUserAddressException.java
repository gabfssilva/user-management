package br.com.user.management.chains.exceptions;

import br.com.user.management.exceptions.UserManagementException;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class AbsentUserAddressException extends UserManagementException {
    public AbsentUserAddressException(String message) {
        super(message);
    }
}
