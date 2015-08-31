package br.com.user.management.chains.v1.commands;

import br.com.user.management.chains.BaseCommand;
import br.com.user.management.chains.Command;
import br.com.user.management.chains.v1.UserContext;
import br.com.user.management.domain.User;
import br.com.user.management.domain.UserStatus;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import javax.inject.Inject;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Command
public class FetchUserByIdCommand extends BaseCommand<UserContext> {
    @Inject
    private Datastore datastore;

    @Override
    public boolean execute(UserContext context) throws Exception {
        final Query<User> query = datastore.createQuery(User.class).filter("_id ==", new ObjectId(context.getId())).filter("status ==", UserStatus.ACTIVE.toString());
        context.setUser(query.get());
        return false;
    }
}
