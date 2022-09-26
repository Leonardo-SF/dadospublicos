package br.com.dadospublicos.crawler.interfaces;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;

public interface API<T> {

    T extrairInformacao() throws CrawlerException, ArquivoException;

}
