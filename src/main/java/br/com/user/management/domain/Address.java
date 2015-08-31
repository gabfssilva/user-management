package br.com.user.management.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class Address {
    private String zipcode;
    private String district;
    private String city;
    private String state;
    private String street;
    private Integer number;
    private String complementaryAddress;

    public Address() {
    }

    private Address(Builder builder) {
        setZipcode(builder.zipcode);
        setDistrict(builder.district);
        setCity(builder.city);
        setState(builder.state);
        setStreet(builder.street);
        setNumber(builder.number);
        setComplementaryAddress(builder.complementaryAddress);
    }

    public static Builder newAddress() {
        return new Builder();
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplementaryAddress() {
        return complementaryAddress;
    }

    public void setComplementaryAddress(String complementaryAddress) {
        this.complementaryAddress = complementaryAddress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "zipcode='" + zipcode + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", complementaryAddress='" + complementaryAddress + '\'' +
                '}';
    }

    public static final class Builder {
        private String zipcode;
        private String district;
        private String city;
        private String state;
        private String street;
        private Integer number;
        private String complementaryAddress;

        private Builder() {
        }

        public Builder zipcode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }

        public Builder district(String district) {
            this.district = district;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder number(Integer number) {
            this.number = number;
            return this;
        }

        public Builder complementaryAddress(String complementaryAddress) {
            this.complementaryAddress = complementaryAddress;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
