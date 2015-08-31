package br.com.user.management.api;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public interface Converter<T, R> {
    R convert(T obj);
}
