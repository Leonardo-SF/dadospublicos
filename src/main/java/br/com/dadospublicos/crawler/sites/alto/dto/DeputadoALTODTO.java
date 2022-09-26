package br.com.dadospublicos.crawler.sites.alto.dto;

import java.util.ArrayList;
import java.util.List;

public class DeputadoALTODTO {

    private String cpf;

    private String nomeDeputado;

    private String partido;

    private String email;

    private String telefone;

    private String biografia;

    private String nomeCompleto;

    private List<DespesaItemALTODTO> despesas = new ArrayList<>();

    public String getCpf() {
        return cpf;
    }

    public DeputadoALTODTO setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public List<DespesaItemALTODTO> getDespesas() {
        return despesas;
    }

    public DeputadoALTODTO setDespesas(List<DespesaItemALTODTO> despesas) {
        this.despesas = despesas;
        return this;
    }

    public String getNomeDeputado() {
        return nomeDeputado;
    }

    public DeputadoALTODTO setNomeDeputado(String nomeDeputado) {
        this.nomeDeputado = nomeDeputado;
        return this;
    }

    public String getPartido() {
        return partido;
    }

    public DeputadoALTODTO setPartido(String partido) {
        this.partido = partido;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public DeputadoALTODTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public DeputadoALTODTO setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public String getBiografia() {
        return biografia;
    }

    public DeputadoALTODTO setBiografia(String biografia) {
        this.biografia = biografia;
        return this;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public DeputadoALTODTO setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
        return this;
    }
}
