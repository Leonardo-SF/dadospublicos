package br.com.dadospublicos.crawler.sites.almg;

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
public class CrawlerDeputadosMG implements Crawler {

    private final Navegador navegador;

    private static final String ALMG_URL = "https://www.almg.gov.br";
    private static final String API_URL = "http://dadosabertos.almg.gov.br";

    public CrawlerDeputadosMG(Navegador navegador) {
        this.navegador = navegador;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Politico> buscarPorMandato() throws CrawlerException, ArquivoException {
        return (List<Politico>) navegador.get(ALMG_URL + "/deputados/conheca_deputados/index.html");
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
        for (var politico : politicos)
            politico.setDespesas(buscarDespesasPorDeputadoEData(politico.getMatricula(), data));

        return politicos;
    }

    @Override
    public List<Politico> buscarInformacoesGabinete(List<Politico> politicos) throws CrawlerException, ArquivoException {
        throw new CrawlerException.OperacaoNaoSuportadaException();
    }

    @SuppressWarnings("unchecked")
    private List<Despesa> buscarDespesasPorDeputadoEData(String matricula, LocalDate dataRef) throws CrawlerException, ArquivoException {
        final var url = String.format("%s/ws/prestacao_contas/verbas_indenizatorias/deputados/%s/%d/%d?formato=json",
                API_URL, matricula, dataRef.getYear(), dataRef.getMonthValue());

        return (List<Despesa>) navegador.get(url);
    }


}
