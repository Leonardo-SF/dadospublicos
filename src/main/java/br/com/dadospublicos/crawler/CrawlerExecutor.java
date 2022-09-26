package br.com.dadospublicos.crawler;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.crawler.factory.CrawlerPoliticoFactory;
import org.springframework.stereotype.Component;

@Component
public class CrawlerExecutor {

    private final CrawlerPoliticoFactory crawlerPoliticoFactory;

    public CrawlerExecutor(CrawlerPoliticoFactory crawlerPoliticoFactory) {
        this.crawlerPoliticoFactory = crawlerPoliticoFactory;
    }

    public void atualizarMandato(CrawlerTipo crawlerTipo, String regiao) throws CrawlerException, ArquivoException {
        var crawler = crawlerPoliticoFactory.getCrawler(crawlerTipo.name(), regiao);
//        crawler.atualizarMandato();
    }
}
