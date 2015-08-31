package br.com.user.management.producers;

import br.com.user.management.factories.DatastoreFactory;
import br.com.user.management.producers.qualifiers.InjectableProperties;
import br.com.user.management.producers.qualifiers.Property;
import br.com.user.management.startup.Startup;
import br.com.user.management.util.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import org.apache.http.client.HttpClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static org.apache.http.impl.client.HttpClientBuilder.create;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class ApplicationProducer {
    @Produces
    @ApplicationScoped
    public HttpClient httpClient() {
        return create()
                .setMaxConnTotal(200)
                .build();
    }

    @Produces
    @Named("cepRepositoryUrl")
    public String cepRepositoryUrl(){
        return "http://localhost:8080/cep-repository/api/v1/cep";
    }

    @Produces //não pode ser application scoped porque a classe possui métodos final
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.get();
    }

    @Produces
    @ApplicationScoped
    @Startup
    @Default
    public Datastore datastore(DatastoreFactory datastoreFactory) throws Exception {
        return datastoreFactory.create();
    }

    @Produces
    @Property(value = "")
    public String property(@InjectableProperties Map<String, String> properties, InjectionPoint injectionPoint){
        final Property property = injectionPoint.getAnnotated().getAnnotation(Property.class);
        return properties.get(property.value());
    }

    @ApplicationScoped
    @Produces
    @InjectableProperties
    public Map<String, String> properties() throws IOException {
        Map<String, String> map = new HashMap<>();
        final ResourceBundle bundle = ResourceBundle.getBundle("application");
        bundle.keySet().forEach(key -> map.put(key, bundle.getString(key)));
        return map;
    }
}
