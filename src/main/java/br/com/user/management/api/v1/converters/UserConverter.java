package br.com.user.management.api.v1.converters;

import br.com.user.management.api.Converter;
import br.com.user.management.api.v1.resources.UserResource;
import br.com.user.management.domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static br.com.user.management.domain.User.newUser;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@ApplicationScoped
public class UserConverter implements Converter<UserResource, User> {
    @Inject
    private AddressConverter addressConverter;

    @Override
    public User convert(UserResource obj) {
        return newUser()
                    .id(obj.getId())
                    .name(obj.getName())
                    .address(obj.getAddress() != null ? addressConverter.convert(obj.getAddress()) : null)
                .build();
    }
}
