package br.com.user.management.api.v1.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class AddressResource {
    @JsonProperty("id")
    private String id;

    @NotNull(message = "zipcode cannot be null")
    @JsonProperty("zipcode")
    private String zipcode;

    @NotNull(message = "street cannot be null")
    @JsonProperty("street")
    private String street;

    @Min(0)
    @NotNull(message = "number cannot be null")
    @JsonProperty("number")
    private Integer number;

    @JsonProperty("complementary_address")
    private String complementaryAddress;

    @JsonProperty("district")
    private String district;

    @NotNull(message = "city cannot be null")
    @JsonProperty("city")
    private String city;

    @NotNull(message = "state cannot be null")
    @JsonProperty("state")
    private String state;

    public AddressResource() {
    }

    private AddressResource(Builder builder) {
        setId(builder.id);
        setZipcode(builder.zipcode);
        setStreet(builder.street);
        setNumber(builder.number);
        setComplementaryAddress(builder.complementaryAddress);
        setDistrict(builder.district);
        setCity(builder.city);
        setState(builder.state);
    }

    public static Builder newAddressResource() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    @Override
    public String toString() {
        return "AddressResource{" +
                "id=" + id +
                ", zipcode='" + zipcode + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", complementaryAddress='" + complementaryAddress + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }


    public static final class Builder {
        private String id;
        private String zipcode;
        private String street;
        private Integer number;
        private String complementaryAddress;
        private String district;
        private String city;
        private String state;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder zipcode(String zipcode) {
            this.zipcode = zipcode;
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

        public AddressResource build() {
            return new AddressResource(this);
        }
    }
}
