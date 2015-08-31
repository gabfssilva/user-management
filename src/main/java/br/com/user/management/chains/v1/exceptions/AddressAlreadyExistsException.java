package br.com.user.management.chains.v1.exceptions;

import br.com.user.management.domain.Address;
import br.com.user.management.exceptions.UserManagementException;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class AddressAlreadyExistsException extends UserManagementException{
    private String id;
    private Address address;

    public AddressAlreadyExistsException(String id, Address address) {
        super("User address with id=" + id + " already exists");
        this.address = address;
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }
}
