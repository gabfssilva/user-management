package br.com.user.management.api.exceptions.mappers;

import br.com.user.management.chains.exceptions.CepNotFoundException;
import br.com.user.management.util.Envelop;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Provider
public class CepNotFoundExceptionMapper implements ExceptionMapper<CepNotFoundException> {
    @Override
    public Response toResponse(CepNotFoundException exception) {
        return Response.status(400).entity(Envelop.newEnvelop().error(new Envelop.ErrorMessage(exception.getMessage())).build()).build();
    }
}
