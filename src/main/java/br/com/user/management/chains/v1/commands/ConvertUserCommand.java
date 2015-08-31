package br.com.user.management.chains.v1.commands;

import br.com.user.management.api.v1.converters.UserConverter;
import br.com.user.management.chains.BaseCommand;
import br.com.user.management.chains.Command;
import br.com.user.management.chains.v1.UserContext;

import javax.inject.Inject;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Command
public class ConvertUserCommand extends BaseCommand<UserContext> {
    @Inject
    private UserConverter userConverter;

    @Override
    public boolean execute(UserContext context) throws Exception {
        context.setUser(userConverter.convert(context.getUserResource()));
        return false;
    }
}
