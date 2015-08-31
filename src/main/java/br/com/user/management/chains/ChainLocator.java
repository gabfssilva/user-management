package br.com.user.management.chains;

import org.apache.commons.chain.impl.ChainBase;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@ApplicationScoped
public class ChainLocator {
    @Inject
    @Any
    private Instance<ChainBase> chains;

    public ChainBase chain(String chainName){
        return chains.select(new ChainQualifier(chainName)).get();
    }
}
