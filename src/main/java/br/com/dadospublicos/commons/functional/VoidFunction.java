package br.com.dadospublicos.commons.functional;

@FunctionalInterface
public interface VoidFunction<E extends Exception> {

    void apply() throws E;

}
