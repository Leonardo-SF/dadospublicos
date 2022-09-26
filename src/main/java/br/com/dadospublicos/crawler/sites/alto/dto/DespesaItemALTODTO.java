package br.com.dadospublicos.crawler.sites.alto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesaItemALTODTO {

    private String descricao;

    private String subElemento;

    private LocalDate dataEmissao;

    private BigDecimal valor;

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public DespesaItemALTODTO setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public DespesaItemALTODTO setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public DespesaItemALTODTO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public String getSubElemento() {
        return subElemento;
    }

    public DespesaItemALTODTO setSubElemento(String subElemento) {
        this.subElemento = subElemento;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DespesaItemALTODTO)) return false;

        DespesaItemALTODTO that = (DespesaItemALTODTO) o;

        return getSubElemento().equals(that.getSubElemento());
    }

    @Override
    public int hashCode() {
        return getSubElemento().hashCode();
    }
}
