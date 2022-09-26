package br.com.dadospublicos.commons.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    private BigDecimalUtils() {}

    public static BigDecimal from(String valor) {
        if (valor == null || valor.isEmpty())
            return null;

        valor = valor.replace("R$", "");
        valor = valor.replace(".", "");

        return new BigDecimal(valor.replace(",", ".").trim());
    }
}
