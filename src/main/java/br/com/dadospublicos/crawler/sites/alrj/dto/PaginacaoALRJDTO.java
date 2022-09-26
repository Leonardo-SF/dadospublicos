package br.com.dadospublicos.crawler.sites.alrj.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaginacaoALRJDTO<T> {

    @JsonProperty("current_page")
    private Integer paginaAtual;

    @JsonProperty("last_page")
    private Integer ultimaPagina;

    @JsonProperty("from")
    private Integer inicio;

    @JsonProperty("to")
    private Integer fim;

    private Integer total;

    @JsonProperty("per_page")
    private Integer qtdePorPagina;

    @JsonProperty("first_page_url")
    private String urlPrimeiraPagina;

    @JsonProperty("last_page_url")
    private String urlUltimaPagina;

    @JsonProperty("nextt_page_url")
    private String urlProximaPagina;

    @JsonProperty("data")
    private List<T> dados;

    public Integer getPaginaAtual() {
        return paginaAtual;
    }

    public PaginacaoALRJDTO setPaginaAtual(Integer paginaAtual) {
        this.paginaAtual = paginaAtual;
        return this;
    }

    public Integer getUltimaPagina() {
        return ultimaPagina;
    }

    public PaginacaoALRJDTO setUltimaPagina(Integer ultimaPagina) {
        this.ultimaPagina = ultimaPagina;
        return this;
    }

    public Integer getInicio() {
        return inicio;
    }

    public PaginacaoALRJDTO setInicio(Integer inicio) {
        this.inicio = inicio;
        return this;
    }

    public Integer getFim() {
        return fim;
    }

    public PaginacaoALRJDTO setFim(Integer fim) {
        this.fim = fim;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public PaginacaoALRJDTO setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public Integer getQtdePorPagina() {
        return qtdePorPagina;
    }

    public PaginacaoALRJDTO setQtdePorPagina(Integer qtdePorPagina) {
        this.qtdePorPagina = qtdePorPagina;
        return this;
    }

    public String getUrlPrimeiraPagina() {
        return urlPrimeiraPagina;
    }

    public PaginacaoALRJDTO setUrlPrimeiraPagina(String urlPrimeiraPagina) {
        this.urlPrimeiraPagina = urlPrimeiraPagina;
        return this;
    }

    public String getUrlUltimaPagina() {
        return urlUltimaPagina;
    }

    public PaginacaoALRJDTO setUrlUltimaPagina(String urlUltimaPagina) {
        this.urlUltimaPagina = urlUltimaPagina;
        return this;
    }

    public String getUrlProximaPagina() {
        return urlProximaPagina;
    }

    public PaginacaoALRJDTO setUrlProximaPagina(String urlProximaPagina) {
        this.urlProximaPagina = urlProximaPagina;
        return this;
    }

    public List<T> getDados() {
        return dados;
    }

    public PaginacaoALRJDTO<T> setDados(List<T> dados) {
        this.dados = dados;
        return this;
    }
}
