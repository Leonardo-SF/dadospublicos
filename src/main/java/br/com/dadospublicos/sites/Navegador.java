package br.com.dadospublicos.sites;

import br.com.dadospublicos.crawler.interfaces.API;
import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Navegador {

    private final ApplicationContext applicationContext;

    public Navegador(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Object get(String url) throws CrawlerException, ArquivoException {
        for (var entry : URL.MAPA.entrySet()) {
            if (entry.getKey().matcher(url).find())
                return ((API<?>)this.applicationContext.getBean(entry.getValue(), url)).extrairInformacao();
        }

        throw new CrawlerException.PaginaNaoEncontradaException("A URL " + url + " ainda não foi mapeada!");
    }
}
