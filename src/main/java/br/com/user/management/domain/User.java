package br.com.user.management.domain;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;

import java.util.Date;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Entity
public class User {
    @Id
    private String id;
    private Date creationDate;
    private String name;
    private UserStatus status;
    @Embedded
    private Address address;

    public User() {
    }

    private User(Builder builder) {
        setId(builder.id);
        setCreationDate(builder.creationDate);
        setName(builder.name);
        setAddress(builder.address);
    }

    public static Builder newUser() {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @PrePersist
    public void prePersist(){
        status = UserStatus.ACTIVE;
        creationDate = new Date();
    }

    public static final class Builder {
        private String id;
        private Date creationDate;
        private String name;
        private Address address;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder creationDate(Date creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
