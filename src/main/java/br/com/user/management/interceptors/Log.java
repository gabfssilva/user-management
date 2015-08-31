package br.com.user.management.interceptors;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface Log {
}
