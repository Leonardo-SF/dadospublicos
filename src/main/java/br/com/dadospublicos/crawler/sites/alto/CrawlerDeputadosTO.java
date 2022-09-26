package br.com.dadospublicos.crawler.sites.alto;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.crawler.interfaces.Crawler;
import br.com.dadospublicos.model.politico.Politico;
import br.com.dadospublicos.sites.Navegador;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class CrawlerDeputadosTO implements Crawler {

    private final Navegador navegador;

    public CrawlerDeputadosTO(Navegador navegador) {
        this.navegador = navegador;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Politico> buscarPorMandato() throws CrawlerException, ArquivoException {
        return (List<Politico>) navegador.get("https://al.to.leg.br/perfil");
    }

    @Override
    public List<Politico> buscarInformacoesGerais(List<String> perfis) throws CrawlerException, ArquivoException {
        var retorno =  visitarPerfis(perfis);

        var politicos = new ArrayList<Politico>(retorno.deputados.size());
        retorno.deputados.drainTo(politicos);

        return politicos;
    }

    private Wrapper visitarPerfis(List<String> perfis) {
        var erros = new ArrayList<String>();
        var deputados = new ArrayBlockingQueue<Politico>(perfis.size());

        var executor = Executors.newFixedThreadPool(10);

        for (var perfil : perfis) {
            executor.execute(() -> {
                try {
                    var deputado = (Politico) navegador.get(perfil);
                    deputados.add(deputado);
                } catch (Exception e) {
                    erros.add(e.getMessage());
                }
            });
        }

        executor.shutdown();

        try {
            if (executor.awaitTermination(5, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        var wrapper = new Wrapper();
        wrapper.deputados = deputados;
        wrapper.erros = erros;

        return wrapper;
    }

    @Override
    public List<Politico> buscarDespesas(List<Politico> politicos, LocalDate data) throws CrawlerException, ArquivoException {
        throw new CrawlerException.OperacaoNaoSuportadaException();
    }

    @Override
    public List<Politico> buscarInformacoesGabinete(List<Politico> politicos) throws CrawlerException, ArquivoException {
        throw new CrawlerException.OperacaoNaoSuportadaException();
    }

    private static class Wrapper {
        BlockingQueue<Politico> deputados = null;
        List<String> erros = null;
    }
}
