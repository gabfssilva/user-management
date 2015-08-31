package br.com.user.management.startup;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Startup {
}
