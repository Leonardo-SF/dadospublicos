package br.com.dadospublicos.model;

import java.math.BigDecimal;

public class Renda {

    private BigDecimal remuneracaoBruta;
    private BigDecimal remuneracaoLiquida;
    private BigDecimal tributos;
    private BigDecimal abonoDePermanencia;
    private BigDecimal feriasBruto;
    private BigDecimal feriasDesconto;
    private BigDecimal feriasLiquida;
    private BigDecimal decimoTerceiroBruto;
    private BigDecimal decimoTerceiroDesconto;
    private BigDecimal decimoTerceiroLiquido;
    private BigDecimal retroativoBruto;
    private BigDecimal retroativoLiquido;
    private BigDecimal retroativoDesconto;
    private BigDecimal outrosBruto;
    private BigDecimal outrosLiquido;
    private BigDecimal outrosDesconto;
    private BigDecimal indenizacao;
    private String mesRef;

    public BigDecimal getRemuneracaoBruta() {
        return remuneracaoBruta;
    }

    public Renda setRemuneracaoBruta(BigDecimal remuneracaoBruta) {
        this.remuneracaoBruta = remuneracaoBruta;
        return this;
    }

    public BigDecimal getRemuneracaoLiquida() {
        return remuneracaoLiquida;
    }

    public Renda setRemuneracaoLiquida(BigDecimal remuneracaoLiquida) {
        this.remuneracaoLiquida = remuneracaoLiquida;
        return this;
    }

    public BigDecimal getTributos() {
        return tributos;
    }

    public Renda setTributos(BigDecimal tributos) {
        this.tributos = tributos;
        return this;
    }

    public BigDecimal getAbonoDePermanencia() {
        return abonoDePermanencia;
    }

    public Renda setAbonoDePermanencia(BigDecimal abonoDePermanencia) {
        this.abonoDePermanencia = abonoDePermanencia;
        return this;
    }

    public BigDecimal getFeriasBruto() {
        return feriasBruto;
    }

    public Renda setFeriasBruto(BigDecimal feriasBruto) {
        this.feriasBruto = feriasBruto;
        return this;
    }

    public BigDecimal getFeriasDesconto() {
        return feriasDesconto;
    }

    public Renda setFeriasDesconto(BigDecimal feriasDesconto) {
        this.feriasDesconto = feriasDesconto;
        return this;
    }

    public BigDecimal getFeriasLiquida() {
        return feriasLiquida;
    }

    public Renda setFeriasLiquida(BigDecimal feriasLiquida) {
        this.feriasLiquida = feriasLiquida;
        return this;
    }

    public BigDecimal getDecimoTerceiroBruto() {
        return decimoTerceiroBruto;
    }

    public Renda setDecimoTerceiroBruto(BigDecimal decimoTerceiroBruto) {
        this.decimoTerceiroBruto = decimoTerceiroBruto;
        return this;
    }

    public BigDecimal getDecimoTerceiroDesconto() {
        return decimoTerceiroDesconto;
    }

    public Renda setDecimoTerceiroDesconto(BigDecimal decimoTerceiroDesconto) {
        this.decimoTerceiroDesconto = decimoTerceiroDesconto;
        return this;
    }

    public BigDecimal getDecimoTerceiroLiquido() {
        return decimoTerceiroLiquido;
    }

    public Renda setDecimoTerceiroLiquido(BigDecimal decimoTerceiroLiquido) {
        this.decimoTerceiroLiquido = decimoTerceiroLiquido;
        return this;
    }

    public BigDecimal getRetroativoBruto() {
        return retroativoBruto;
    }

    public Renda setRetroativoBruto(BigDecimal retroativoBruto) {
        this.retroativoBruto = retroativoBruto;
        return this;
    }

    public BigDecimal getRetroativoLiquido() {
        return retroativoLiquido;
    }

    public Renda setRetroativoLiquido(BigDecimal retroativoLiquido) {
        this.retroativoLiquido = retroativoLiquido;
        return this;
    }

    public BigDecimal getRetroativoDesconto() {
        return retroativoDesconto;
    }

    public Renda setRetroativoDesconto(BigDecimal retroativoDesconto) {
        this.retroativoDesconto = retroativoDesconto;
        return this;
    }

    public BigDecimal getOutrosBruto() {
        return outrosBruto;
    }

    public Renda setOutrosBruto(BigDecimal outrosBruto) {
        this.outrosBruto = outrosBruto;
        return this;
    }

    public BigDecimal getOutrosLiquido() {
        return outrosLiquido;
    }

    public Renda setOutrosLiquido(BigDecimal outrosLiquido) {
        this.outrosLiquido = outrosLiquido;
        return this;
    }

    public BigDecimal getOutrosDesconto() {
        return outrosDesconto;
    }

    public Renda setOutrosDesconto(BigDecimal outrosDesconto) {
        this.outrosDesconto = outrosDesconto;
        return this;
    }

    public BigDecimal getIndenizacao() {
        return indenizacao;
    }

    public Renda setIndenizacao(BigDecimal indenizacao) {
        this.indenizacao = indenizacao;
        return this;
    }

    public String getMesRef() {
        return mesRef;
    }

    public Renda setMesRef(String mesRef) {
        this.mesRef = mesRef;
        return this;
    }
}
