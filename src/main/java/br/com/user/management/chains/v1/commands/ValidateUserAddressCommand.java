package br.com.user.management.chains.v1.commands;

import br.com.user.management.chains.BaseCommand;
import br.com.user.management.chains.Command;
import br.com.user.management.chains.exceptions.CepNotFoundException;
import br.com.user.management.chains.v1.UserContext;
import br.com.user.management.client.CepClient;
import br.com.user.management.client.resources.Cep;

import javax.inject.Inject;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Command
public class ValidateUserAddressCommand extends BaseCommand<UserContext> {
    @Inject
    private CepClient cepClient;

    @Override
    public boolean execute(UserContext context) throws Exception {
        if(context.getAddress() == null){
            return false;
        }

        final String zipcode = context.getAddress().getZipcode().replace("-", "");
        final Cep cepClientCep = cepClient.findCep(zipcode);

        if(cepClientCep == null || !zipcode.equals(cepClientCep.getCep())){
            throw new CepNotFoundException("The given CEP was not found");
        }

        //TODO: seria necessário validar nome de rua, bairro, cidade, estado...?
        return false;
    }
}
