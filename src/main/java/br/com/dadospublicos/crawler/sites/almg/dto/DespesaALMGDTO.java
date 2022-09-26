package br.com.dadospublicos.crawler.sites.almg.dto;

import java.util.List;

public class DespesaALMGDTO {

    private Long idDeputado;

    private String descTipoDespesa;

    private List<DespesaDetalheALMGDTO> listaDetalheVerba;

    public Long getIdDeputado() {
        return idDeputado;
    }

    public DespesaALMGDTO setIdDeputado(Long idDeputado) {
        this.idDeputado = idDeputado;
        return this;
    }

    public String getDescTipoDespesa() {
        return descTipoDespesa;
    }

    public DespesaALMGDTO setDescTipoDespesa(String descTipoDespesa) {
        this.descTipoDespesa = descTipoDespesa;
        return this;
    }

    public List<DespesaDetalheALMGDTO> getListaDetalheVerba() {
        return listaDetalheVerba;
    }

    public DespesaALMGDTO setListaDetalheVerba(List<DespesaDetalheALMGDTO> listaDetalheVerba) {
        this.listaDetalheVerba = listaDetalheVerba;
        return this;
    }
}
