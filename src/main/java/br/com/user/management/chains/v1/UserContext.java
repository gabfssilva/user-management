package br.com.user.management.chains.v1;

import br.com.user.management.api.v1.resources.AddressResource;
import br.com.user.management.api.v1.resources.UserResource;
import br.com.user.management.domain.Address;
import br.com.user.management.domain.User;
import org.apache.commons.chain.impl.ContextBase;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class UserContext extends ContextBase {
    private String id;
    private User user;
    private AddressResource addressResource;
    private Address address;
    private UserResource userResource;

    public UserContext(String id, AddressResource addressResource) {
        this.id = id;
        this.addressResource = addressResource;
    }

    public UserContext(String id, UserResource userResource) {
        this.id = id;
        this.userResource = userResource;
    }

    public UserContext(UserResource userResource) {
        this.userResource = userResource;
    }

    public UserContext(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public UserContext(String id) {
        this.id = id;
    }

    public UserContext(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserResource getUserResource() {
        return userResource;
    }

    public void setUserResource(UserResource userResource) {
        this.userResource = userResource;
    }

    public AddressResource getAddressResource() {
        if(userResource != null && userResource.getAddress() != null){
            return userResource.getAddress();
        }

        return addressResource;
    }

    public Address getAddress() {
        if(user != null && user.getAddress() != null){
            return user.getAddress();
        }

        return address;
    }

    public void setAddressResource(AddressResource addressResource) {
        this.addressResource = addressResource;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "id=" + id +
                ", user=" + user +
                ", addressResource=" + addressResource +
                ", address=" + address +
                ", userResource=" + userResource +
                "} " + super.toString();
    }
}
