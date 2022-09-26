package br.com.dadospublicos.commons.utils;

import java.time.LocalDate;

public class MesRefUtils {

    private static final String FORMATO =  "%02d%d";

    private MesRefUtils() {}

    public static String getMesRef(int mes, int ano) {
        return String.format(FORMATO, mes, ano);
    }

    public static String getMesRef(String mes, String ano) {
        return getMesRef(Integer.parseInt(mes), Integer.parseInt(ano));
    }

    public static String getMesRef(LocalDate data) {
        return getMesRef(data.getMonthValue(), data.getYear());
    }

}
