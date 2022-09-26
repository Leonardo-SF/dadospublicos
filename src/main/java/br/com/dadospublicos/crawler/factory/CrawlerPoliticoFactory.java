package br.com.dadospublicos.crawler.factory;

import br.com.dadospublicos.crawler.interfaces.Crawler;
import org.springframework.stereotype.Component;

@Component
public class CrawlerPoliticoFactory {

    private final CrawlerDeputadoFactory crawlerDeputadoFactory;

    public CrawlerPoliticoFactory(CrawlerDeputadoFactory crawlerDeputadoFactory) {
        this.crawlerDeputadoFactory = crawlerDeputadoFactory;
    }

    public Crawler getCrawler(String tipo, String regiao) {
        if ("DEPUTADO".equalsIgnoreCase(tipo)) {
            return crawlerDeputadoFactory.getCrawler(regiao);
        }

        return null;
    }

}
