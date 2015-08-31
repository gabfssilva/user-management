package br.com.user.management.api.v1.exceptions.mappers;

import br.com.user.management.api.v1.converters.AddressResourceConverter;
import br.com.user.management.api.v1.resources.AddressResource;
import br.com.user.management.chains.v1.exceptions.AddressAlreadyExistsException;
import br.com.user.management.util.Envelop;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static br.com.user.management.util.Envelop.newEnvelop;
import static javax.ws.rs.core.Response.status;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Provider
public class AddressAlreadyExistsExceptionMapper implements ExceptionMapper<AddressAlreadyExistsException> {
    @Inject
    private AddressResourceConverter addressResourceConverter;

    @Override
    public Response toResponse(AddressAlreadyExistsException exception) {
        final AddressResource addressResource = addressResourceConverter.convert(exception.getAddress());
        addressResource.setId(exception.getId());

        return status(409)
                .entity(newEnvelop()
                        .error(new Envelop.ErrorMessage(exception.getMessage()))
                        .item(addressResource)
                        .build())
                .build();
    }
}
