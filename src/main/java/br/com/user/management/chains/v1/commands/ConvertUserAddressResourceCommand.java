package br.com.user.management.chains.v1.commands;

import br.com.user.management.api.v1.converters.AddressResourceConverter;
import br.com.user.management.chains.BaseCommand;
import br.com.user.management.chains.Command;
import br.com.user.management.chains.v1.UserContext;

import javax.inject.Inject;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Command
public class ConvertUserAddressResourceCommand extends BaseCommand<UserContext> {
    @Inject
    private AddressResourceConverter addressConverter;

    @Override
    public boolean execute(UserContext context) throws Exception {
        context.setAddressResource(addressConverter.convert(context.getAddress()));
        context.getAddressResource().setId(context.getId());
        return false;
    }
}
