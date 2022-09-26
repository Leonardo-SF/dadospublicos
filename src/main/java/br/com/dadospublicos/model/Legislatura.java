package br.com.dadospublicos.model;

public class Legislatura {

    private Long numero;

    private Long anoInicial;

    private Long anoFinal;

    public Long getNumero() {
        return numero;
    }

    public Legislatura setNumero(Long numero) {
        this.numero = numero;
        return this;
    }

    public Long getAnoInicial() {
        return anoInicial;
    }

    public Legislatura setAnoInicial(Long anoInicial) {
        this.anoInicial = anoInicial;
        return this;
    }

    public Long getAnoFinal() {
        return anoFinal;
    }

    public Legislatura setAnoFinal(Long anoFinal) {
        this.anoFinal = anoFinal;
        return this;
    }
}
