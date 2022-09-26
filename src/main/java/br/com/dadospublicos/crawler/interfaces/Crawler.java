package br.com.dadospublicos.crawler.interfaces;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.model.politico.Politico;

import java.time.LocalDate;
import java.util.List;

public interface Crawler {

    List<Politico> buscarPorMandato() throws CrawlerException, ArquivoException;

    List<Politico> buscarInformacoesGerais(List<String> perfis) throws CrawlerException, ArquivoException;

    List<Politico> buscarDespesas(List<Politico> politicos, LocalDate data) throws CrawlerException, ArquivoException;

    List<Politico> buscarInformacoesGabinete(List<Politico> politicos) throws CrawlerException, ArquivoException;

}
