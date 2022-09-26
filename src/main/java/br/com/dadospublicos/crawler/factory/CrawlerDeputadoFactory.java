package br.com.dadospublicos.crawler.factory;

import br.com.dadospublicos.crawler.interfaces.Crawler;
import br.com.dadospublicos.crawler.sites.alsp.CrawlerDeputadosSP;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
class CrawlerDeputadoFactory {

    private final Map<String, Crawler> deputados = new HashMap<>();

    public CrawlerDeputadoFactory(CrawlerDeputadosSP crawlerDeputadosSP) {
        deputados.put("SP", crawlerDeputadosSP);
    }

    public Crawler getCrawler(String regiao) {
        if ("SP".equalsIgnoreCase(regiao)) {
            return deputados.get(regiao);
        }

        return null;
    }

}
