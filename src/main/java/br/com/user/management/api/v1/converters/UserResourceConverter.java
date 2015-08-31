package br.com.user.management.api.v1.converters;

import br.com.user.management.api.Converter;
import br.com.user.management.api.v1.resources.AddressResource;
import br.com.user.management.api.v1.resources.UserResource;
import br.com.user.management.domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static br.com.user.management.api.v1.resources.UserResource.newUserResource;


/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@ApplicationScoped
public class UserResourceConverter implements Converter<User, UserResource> {
    @Inject
    private AddressResourceConverter addressConverter;

    @Override
    public UserResource convert(User obj) {
        final AddressResource address = obj.getAddress() != null ? addressConverter.convert(obj.getAddress()) : null;

        if (address != null) {
            address.setId(obj.getId());
        }

        return newUserResource()
                    .id(obj.getId())
                    .name(obj.getName())
                    .address(address)
                .build();
    }
}
