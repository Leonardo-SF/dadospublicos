package br.com.dadospublicos.crawler.sites.alsp;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.crawler.interfaces.Crawler;
import br.com.dadospublicos.model.Despesa;
import br.com.dadospublicos.model.politico.Politico;
import br.com.dadospublicos.sites.Navegador;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrawlerDeputadosSP implements Crawler {

    private final Navegador navegador;

    private static final String ALSP_URL = "https://www.al.sp.gov.br";

    public CrawlerDeputadosSP(Navegador navegador) {
        this.navegador = navegador;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Politico> buscarPorMandato() throws CrawlerException, ArquivoException {
        return (List<Politico>) navegador.get(ALSP_URL + "/deputado/lista");
    }

    @Override
    public List<Politico> buscarInformacoesGerais(List<String> perfis) throws CrawlerException, ArquivoException {
        var deputados = new ArrayList<Politico>(perfis.size());
        for (var perfil : perfis) {
            var deputado = (Politico) navegador.get(perfil);
            deputados.add(deputado);
        }
        return deputados;
    }

    @Override
    public List<Politico> buscarDespesas(List<Politico> politicos, LocalDate data) throws CrawlerException, ArquivoException {
        for (var politico : politicos) {
            var despesas = this.buscarDespesasPorDeputadoEData(politico.getMatricula(), data);
            politico.setDespesas(despesas);
        }

        return politicos;
    }

    @Override
    public List<Politico> buscarInformacoesGabinete(List<Politico> politicos) throws CrawlerException, ArquivoException {
        var funcionariosGabinete = new ArrayList<Politico>();

        for (var politico : politicos)
            funcionariosGabinete.addAll(buscarServidoresPorDeputado(politico.getIdUA()));

        return funcionariosGabinete;
    }

    @SuppressWarnings("unchecked")
    public List<Despesa> buscarDespesasPorDeputadoEData(String matricula, LocalDate dataRef) throws CrawlerException, ArquivoException {
        final var url = String.format("%s/deputado/contas/?matricula=%s&mes=%s&ano=%s&tipo=naturezas",
                ALSP_URL, matricula, dataRef.getMonthValue(), dataRef.getYear());

        return (List<Despesa>) navegador.get(url);
    }

    @SuppressWarnings("unchecked")
    public List<Politico> buscarServidoresPorDeputado(Integer idUA) throws CrawlerException, ArquivoException {
        final var url = String.format("%s/servidor/lista/?idUA=%d&tpSituacao=T", ALSP_URL, idUA);
        var perfis = (List<String>) navegador.get(url);

        var agentes = new ArrayList<Politico>(perfis.size());

        for (var perfil : perfis) {
            var agente = (Politico) navegador.get(perfil);
            agentes.add(agente);
        }

        return agentes;
    }

}
