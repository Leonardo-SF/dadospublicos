package br.com.dadospublicos.commons.functional;

@FunctionalInterface
public interface ConsumerThrowable<V, E extends Exception> {

    void apply(V v) throws E;

}
