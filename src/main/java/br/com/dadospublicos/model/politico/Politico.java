package br.com.dadospublicos.model.politico;

import br.com.dadospublicos.model.Cargo;
import br.com.dadospublicos.model.Despesa;
import br.com.dadospublicos.model.Renda;

import java.util.List;

public class Politico {

    private String nome;

    private String nomeAbreviado;

    private String matricula;

    private String partido;

    private String baseEleitoral;

    private String email;

    private String areaDeAtuacao;

    private String telefone;

    private String sala;

    private String veiculo;

    private String dataAniversario;

    private String biografia;

    private Integer idUA;

    private List<Despesa> despesas;

    private List<Renda> rendas;

    private List<Politico> funcionarios;

    private List<Cargo> cargos;

    private String linkPerfil;

    public Politico() {}

    public String getNome() {
        return nome;
    }

    public Politico setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getNomeAbreviado() {
        return nomeAbreviado;
    }

    public Politico setNomeAbreviado(String nomeAbreviado) {
        this.nomeAbreviado = nomeAbreviado;
        return this;
    }

    public String getMatricula() {
        return matricula;
    }

    public Politico setMatricula(String matricula) {
        this.matricula = matricula;
        return this;
    }

    public String getPartido() {
        return partido;
    }

    public Politico setPartido(String partido) {
        this.partido = partido;
        return this;
    }

    public String getBaseEleitoral() {
        return baseEleitoral;
    }

    public Politico setBaseEleitoral(String baseEleitoral) {
        this.baseEleitoral = baseEleitoral;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Politico setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAreaDeAtuacao() {
        return areaDeAtuacao;
    }

    public Politico setAreaDeAtuacao(String areaDeAtuacao) {
        this.areaDeAtuacao = areaDeAtuacao;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public Politico setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public String getSala() {
        return sala;
    }

    public Politico setSala(String sala) {
        this.sala = sala;
        return this;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public Politico setVeiculo(String veiculo) {
        this.veiculo = veiculo;
        return this;
    }

    public String getDataAniversario() {
        return dataAniversario;
    }

    public Politico setDataAniversario(String dataAniversario) {
        this.dataAniversario = dataAniversario;
        return this;
    }

    public String getBiografia() {
        return biografia;
    }

    public Politico setBiografia(String biografia) {
        this.biografia = biografia;
        return this;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public Politico setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
        return this;
    }

    public List<Renda> getRendas() {
        return rendas;
    }

    public Politico setRendas(List<Renda> rendas) {
        this.rendas = rendas;
        return this;
    }

    public List<Politico> getFuncionarios() {
        return funcionarios;
    }

    public Politico setFuncionarios(List<Politico> funcionarios) {
        this.funcionarios = funcionarios;
        return this;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public Politico setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
        return this;
    }

    public Integer getIdUA() {
        return idUA;
    }

    public Politico setIdUA(Integer idUA) {
        this.idUA = idUA;
        return this;
    }

    public String getLinkPerfil() {
        return linkPerfil;
    }

    public Politico setLinkPerfil(String linkPerfil) {
        this.linkPerfil = linkPerfil;
        return this;
    }

    @Override
    public String toString() {
        return "Deputado{" +
                "nome='" + nome + '\'' +
                ", matricula='" + matricula + '\'' +
                ", partido='" + partido + '\'' +
                ", baseEleitoral='" + baseEleitoral + '\'' +
                ", email='" + email + '\'' +
                ", areaDeAtuacao='" + areaDeAtuacao + '\'' +
                ", telefone='" + telefone + '\'' +
                ", sala='" + sala + '\'' +
                ", veiculo='" + veiculo + '\'' +
                ", dataAniversario='" + dataAniversario + '\'' +
                '}';
    }
}
