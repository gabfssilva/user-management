package br.com.user.management.chains;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public abstract class BaseCommand<T> implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        return execute((T) context);
    }

    public abstract boolean execute(T context) throws Exception;
}