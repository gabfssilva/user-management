package br.com.user.management.factories;

import br.com.user.management.producers.qualifiers.Property;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@ApplicationScoped
public class DefaultDatastoreFactory implements DatastoreFactory {

    @Inject
    @Property("mongodb.uri")
    private String mongoDbUri;

    @Override
    public Datastore create() {
        MongoClientURI uri = new MongoClientURI(mongoDbUri);
        final MongoClient mongoClient = new MongoClient(uri);

        final Morphia morphia = new Morphia();
        morphia.mapPackage("br.com.user.management.domain");

        final Datastore datastore = morphia.createDatastore(mongoClient, "user-management-db");
        datastore.ensureIndexes();
        return datastore;
    }
}
