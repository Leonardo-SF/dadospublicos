package br.com.dadospublicos.commons.functional;

@FunctionalInterface
public interface SupplierThrowable<V, E extends Exception> {
    V apply() throws E;
}
