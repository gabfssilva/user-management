package br.com.user.management.chains.v1.commands;

import br.com.user.management.chains.BaseCommand;
import br.com.user.management.chains.Command;
import br.com.user.management.chains.v1.UserContext;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Command
public class SetUserAddressToContextCommand extends BaseCommand<UserContext> {
    @Override
    public boolean execute(UserContext context) throws Exception {
        context.setAddress(context.getUser() == null ? null : context.getUser().getAddress());
        return false;
    }
}
