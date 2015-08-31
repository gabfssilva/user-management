package br.com.user.management.factories;

import org.mongodb.morphia.Datastore;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public interface DatastoreFactory {
    Datastore create() throws Exception;
}
