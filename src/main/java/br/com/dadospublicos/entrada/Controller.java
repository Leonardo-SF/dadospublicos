package br.com.dadospublicos.entrada;

import br.com.dadospublicos.crawler.CrawlerExecutor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Controller {

    private final CrawlerExecutor crawlerExecutor;

    public Controller(CrawlerExecutor executor) {
        this.crawlerExecutor = executor;
    }

    //@PostMapping('/deputados/{estado}')
    public void teste(Optional<String> uf) {
        //CrawlerTipo.valueOf(processo.getTipo());
        //processo.getRegioes().split(;);
    }

}
