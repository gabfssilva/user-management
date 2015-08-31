package br.com.user.management.api.exceptions.mappers;

import br.com.user.management.chains.exceptions.AbsentUserException;
import br.com.user.management.util.Envelop;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static br.com.user.management.util.Envelop.newEnvelop;
import static javax.ws.rs.core.Response.status;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Provider
public class AbsentUserExceptionMapper implements ExceptionMapper<AbsentUserException> {
    @Override
    public Response toResponse(AbsentUserException exception) {
        return status(404)
                .entity(newEnvelop().error(new Envelop.ErrorMessage(exception.getMessage())).build())
                .build();
    }
}
