package br.com.user.management.chains.v1.commands;

import br.com.user.management.chains.BaseCommand;
import br.com.user.management.chains.Command;
import br.com.user.management.chains.exceptions.AbsentUserAddressException;
import br.com.user.management.chains.v1.UserContext;
import br.com.user.management.domain.User;
import br.com.user.management.domain.UserStatus;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import javax.inject.Inject;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Command
public class RemoveAddressFromUser extends BaseCommand<UserContext> {
    @Inject
    private Datastore datastore;

    @Override
    public boolean execute(UserContext context) throws Exception {
        final UpdateOperations<User> updateOperations = datastore.createUpdateOperations(User.class);

        updateOperations
                .unset("address");

        final Query<User> selectedUser = datastore.createQuery(User.class).filter("_id ==", new ObjectId(context.getId())).filter("status ==", UserStatus.ACTIVE.toString()).field("address").exists();

        if(selectedUser.countAll()  == 0){
            throw new AbsentUserAddressException("The address from the user with id=" + context.getId() + " is null");
        }

        datastore.update(selectedUser, updateOperations);
        return false;
    }
}
