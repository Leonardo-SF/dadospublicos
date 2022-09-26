package br.com.dadospublicos.model;

import java.time.LocalDate;
import java.util.List;

public class Cargo {

    private String nome;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    List<Legislatura> legislaturas;

    public String getNome() {
        return nome;
    }

    public Cargo setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public Cargo setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public Cargo setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public List<Legislatura> getLegislaturas() {
        return legislaturas;
    }

    public Cargo setLegislaturas(List<Legislatura> legislaturas) {
        this.legislaturas = legislaturas;
        return this;
    }
}
