package br.com.user.management.chains.v1.commands;

import br.com.user.management.api.v1.converters.AddressConverter;
import br.com.user.management.api.v1.converters.UserConverter;
import br.com.user.management.chains.BaseCommand;
import br.com.user.management.chains.Command;
import br.com.user.management.chains.v1.UserContext;

import javax.inject.Inject;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Command
public class ConvertUserAddressCommand extends BaseCommand<UserContext> {
    @Inject
    private AddressConverter addressConverter;

    @Override
    public boolean execute(UserContext context) throws Exception {
        context.setAddress(addressConverter.convert(context.getAddressResource()));
        return false;
    }
}
