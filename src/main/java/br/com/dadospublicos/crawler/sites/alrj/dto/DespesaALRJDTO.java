package br.com.dadospublicos.crawler.sites.alrj.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class DespesaALRJDTO {

    private Long id;

    @JsonProperty("created_at")
    private String dataDeCriacao;

    @JsonProperty("entries")
    private List<DespesaItensALRJDTO> itens;

    public Long getId() {
        return id;
    }

    public DespesaALRJDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDataDeCriacao() {
        return dataDeCriacao;
    }

    public DespesaALRJDTO setDataDeCriacao(String dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
        return this;
    }

    public List<DespesaItensALRJDTO> getItens() {
        return itens;
    }

    public DespesaALRJDTO setItens(List<DespesaItensALRJDTO> itens) {
        this.itens = itens;
        return this;
    }

    public static class DespesaItensALRJDTO {

        private Long id;

        @JsonProperty("date")
        private String data;

        @JsonProperty("value")
        private BigDecimal valor;

        @JsonProperty("object")
        private String descricao;

        @JsonProperty("to")
        private String fornecedor;

        public Long getId() {
            return id;
        }

        public DespesaItensALRJDTO setId(Long id) {
            this.id = id;
            return this;
        }

        public String getData() {
            return data;
        }

        public DespesaItensALRJDTO setData(String data) {
            this.data = data;
            return this;
        }

        public BigDecimal getValor() {
            return valor;
        }

        public DespesaItensALRJDTO setValor(BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        public String getDescricao() {
            return descricao;
        }

        public DespesaItensALRJDTO setDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public String getFornecedor() {
            return fornecedor;
        }

        public DespesaItensALRJDTO setFornecedor(String fornecedor) {
            this.fornecedor = fornecedor;
            return this;
        }
    }

}
