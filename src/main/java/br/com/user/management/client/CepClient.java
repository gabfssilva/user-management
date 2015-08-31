package br.com.user.management.client;

import br.com.user.management.client.exceptions.InvalidCepException;
import br.com.user.management.client.resources.Cep;
import br.com.user.management.util.Envelop;
import br.com.user.management.exceptions.UserManagementException;
import br.com.user.management.interceptors.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.String.format;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@ApplicationScoped
@Log
public class CepClient {
    @Inject
    private HttpClient httpClient;

    @Inject
    @Named("cepRepositoryUrl")
    private String cepRepositoryUrl;

    @Inject
    private ObjectMapper objectMapper;

    public Cep findCep(String cep) {
        HttpGet httpGet = new HttpGet(cepRepositoryUrl.concat("/").concat(cep));

        try {
            final HttpResponse httpResponse;
            final InputStream inputStreamResult;

            try {
                httpResponse = httpClient.execute(httpGet);
                inputStreamResult = httpResponse.getEntity().getContent();
            } catch (IOException e) {
                throw new UserManagementException(format("Could not fetch the cep %s", cep), e);
            }

            if (notFound(httpResponse)) {
                return null;
            }

            if (badRequest(httpResponse)) {
                throw new InvalidCepException(format("Could not fetch the cep because the server returned a bad request. Probably the cep %s is invalid", cep));
            }

            try {
                Envelop<Cep> envelopedCep = objectMapper.readValue(inputStreamResult, objectMapper.getTypeFactory().constructParametrizedType(Envelop.class, Envelop.class, Cep.class));
                return envelopedCep.getItem();
            } catch (IOException e) {
                throw new UserManagementException(format("Could not parse result from the search of the cep %s", cep), e);
            }
        } finally {
            httpGet.releaseConnection();
        }
    }

    private void consumeContent(String cep, HttpResponse httpResponse) {
        try {
            httpResponse.getEntity().getContent();
        } catch (IOException e) {
            throw new UserManagementException(format("Could not load the response for the cep=", cep), e);
        }
    }

    private boolean badRequest(HttpResponse httpResponse) {
        return httpResponse.getStatusLine().getStatusCode() == 400;
    }

    private boolean notFound(HttpResponse httpResponse) {
        return httpResponse.getStatusLine().getStatusCode() == 404;
    }
}
