package br.com.user.management.api.v1.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class UserResource {
    @JsonProperty("id")
    private String id;

    @JsonProperty("creation_date")
    private Date creationDate;

    @JsonProperty("name")
    @NotNull(message = "name cannot be null")
    private String name;

    @JsonProperty("address")
    @Valid
    private AddressResource address;

    public UserResource() {
    }

    private UserResource(Builder builder) {
        setId(builder.id);
        setCreationDate(builder.creationDate);
        setName(builder.name);
        setAddress(builder.address);
    }

    public static Builder newUserResource() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressResource getAddress() {
        return address;
    }

    public void setAddress(AddressResource address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }

    public static final class Builder {
        private String id;
        private Date creationDate;
        private String name;
        private AddressResource address;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder creationDate(Date dateCreation) {
            this.creationDate = dateCreation;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(AddressResource address) {
            this.address = address;
            return this;
        }

        public UserResource build() {
            return new UserResource(this);
        }
    }
}
