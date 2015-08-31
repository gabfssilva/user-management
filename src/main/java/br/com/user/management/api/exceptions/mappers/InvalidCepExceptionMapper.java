package br.com.user.management.api.exceptions.mappers;

import br.com.user.management.client.exceptions.InvalidCepException;
import br.com.user.management.util.Envelop;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Provider
public class InvalidCepExceptionMapper implements ExceptionMapper<InvalidCepException> {
    @Override
    public Response toResponse(InvalidCepException exception) {
        return Response.status(400).entity(Envelop.newEnvelop().error(new Envelop.ErrorMessage(exception.getMessage())).build()).build();
    }
}
