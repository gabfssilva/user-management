package br.com.user.management.chains.v1.commands;

import br.com.user.management.chains.BaseCommand;
import br.com.user.management.chains.Command;
import br.com.user.management.chains.exceptions.AbsentUserException;
import br.com.user.management.chains.v1.UserContext;
import br.com.user.management.chains.v1.exceptions.AddressAlreadyExistsException;
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
public class AddAddressToUserCommand extends BaseCommand<UserContext> {
    @Inject
    private Datastore datastore;

    @Override
    public boolean execute(UserContext context) throws Exception {
        final UpdateOperations<User> updateOperations = datastore.createUpdateOperations(User.class);

        if(context.getAddress() != null){
            updateOperations
                    .set("address", context.getAddress());
        }

        final Query<User> selectedUser = datastore.createQuery(User.class).filter("_id ==", new ObjectId(context.getId())).filter("status ==", UserStatus.ACTIVE.toString());

        if(selectedUser.countAll() == 0){
            throw new AbsentUserException("There is no user with id=" + context.getId());
        }

        final User user = selectedUser.get();
        if(user.getAddress() != null){
            throw new AddressAlreadyExistsException(context.getId(), user.getAddress());
        }

        datastore.update(selectedUser, updateOperations);
        return false;
    }
}
