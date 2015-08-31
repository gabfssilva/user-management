package br.com.user.management.api.v1.converters;

import br.com.user.management.api.Converter;
import br.com.user.management.api.v1.resources.AddressResource;
import br.com.user.management.domain.Address;

import javax.enterprise.context.ApplicationScoped;

import static br.com.user.management.domain.Address.newAddress;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@ApplicationScoped
public class AddressConverter implements Converter<AddressResource, Address> {
    @Override
    public Address convert(AddressResource obj) {
        return newAddress()
                    .street(obj.getStreet())
                    .complementaryAddress(obj.getComplementaryAddress())
                    .number(obj.getNumber())
                    .city(obj.getCity())
                    .district(obj.getDistrict())
                    .zipcode(obj.getZipcode())
                    .state(obj.getState())
                .build();
    }
}
