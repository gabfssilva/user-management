package br.com.user.management.chains.v1.commands;

import br.com.user.management.chains.BaseCommand;
import br.com.user.management.chains.Command;
import br.com.user.management.chains.exceptions.AbsentUserException;
import br.com.user.management.chains.v1.UserContext;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Command
public class CheckIfUserIsNotNullCommand extends BaseCommand<UserContext> {
    @Override
    public boolean execute(UserContext context) throws Exception {
        if(context.getUser() == null){
            throw new AbsentUserException("The user with id=" + context.getId() + " is null");
        }

        return false;
    }
}
