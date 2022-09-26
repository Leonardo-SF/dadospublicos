package br.com.dadospublicos.model;

import java.math.BigDecimal;

public class Despesa {

    private String descriacao;

    private BigDecimal valor;

    private String mesRef;

    private String nomeFornecedor;

    private String cnpjCpfFornecedor;

    private Long deputadoId;

    public String getDescriacao() {
        return descriacao;
    }

    public Despesa setDescriacao(String descriacao) {
        this.descriacao = descriacao;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Despesa setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public String getMesRef() {
        return mesRef;
    }

    public Despesa setMesRef(String mesRef) {
        this.mesRef = mesRef;
        return this;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public Despesa setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
        return this;
    }

    public String getCnpjCpfFornecedor() {
        return cnpjCpfFornecedor;
    }

    public Despesa setCnpjCpfFornecedor(String cnpjCpfFornecedor) {
        this.cnpjCpfFornecedor = cnpjCpfFornecedor;
        return this;
    }

    public Long getDeputadoId() {
        return deputadoId;
    }

    public Despesa setDeputadoId(Long deputadoId) {
        this.deputadoId = deputadoId;
        return this;
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "descriacao='" + descriacao + '\'' +
                ", valor=" + valor +
                ", mesRef='" + mesRef + '\'' +
                '}';
    }
}
