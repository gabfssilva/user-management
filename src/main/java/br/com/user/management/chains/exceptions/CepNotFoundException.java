package br.com.user.management.chains.exceptions;

import br.com.user.management.exceptions.UserManagementException;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class CepNotFoundException extends UserManagementException {
    public CepNotFoundException(String message) {
        super(message);
    }
}
