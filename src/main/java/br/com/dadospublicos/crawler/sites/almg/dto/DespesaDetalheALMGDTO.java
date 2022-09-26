package br.com.dadospublicos.crawler.sites.almg.dto;

import java.math.BigDecimal;

public class DespesaDetalheALMGDTO {

    private BigDecimal valorReembolsado;
    private String cpfCnpj;
    private String nomeEmitente;

    public BigDecimal getValorReembolsado() {
        return valorReembolsado;
    }

    public DespesaDetalheALMGDTO setValorReembolsado(BigDecimal valorReembolsado) {
        this.valorReembolsado = valorReembolsado;
        return this;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public DespesaDetalheALMGDTO setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
        return this;
    }

    public String getNomeEmitente() {
        return nomeEmitente;
    }

    public DespesaDetalheALMGDTO setNomeEmitente(String nomeEmitente) {
        this.nomeEmitente = nomeEmitente;
        return this;
    }
}
