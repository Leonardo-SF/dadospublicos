package br.com.dadospublicos.crawler.sites.almg.dto;

import java.util.List;

public class DespesaMGWrapper {

    private List<DespesaALMGDTO> list;

    public List<DespesaALMGDTO> getList() {
        return list;
    }

    public DespesaMGWrapper setList(List<DespesaALMGDTO> list) {
        this.list = list;
        return this;
    }
}
