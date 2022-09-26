package br.com.dadospublicos.crawler.sites.alrj.api;

import br.com.dadospublicos.crawler.sites.alrj.dto.DespesaALRJDTO;
import br.com.dadospublicos.crawler.sites.alrj.dto.PaginacaoALRJDTO;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.model.Despesa;
import br.com.dadospublicos.crawler.interfaces.API;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
public class APIPrestacaoDeContasRJ extends PageFactory implements API<List<Despesa>> {
    private final String url;

    private APIPrestacaoDeContasRJ() {
        this.url = null;
    }

    public APIPrestacaoDeContasRJ(String url) {
        this.url = url;
    }

    @Override
    public List<Despesa> extrairInformacao() throws CrawlerException {
        var paginacao = WebClient
                .create(getUrl())
                .get()
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new CrawlerException.ErroInternoException("Erro na comunicação com o servidor")))
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new CrawlerException.RequisicaoInvalidaException("Erro na requisição " + url)))
                .bodyToMono(new ParameterizedTypeReference<PaginacaoALRJDTO<DespesaALRJDTO>>() {
                })
                .block();

        System.out.println(paginacao.getDados().get(0).getItens().get(0).getValor());
        return null;
    }

    private String getUrl() throws CrawlerException.URLInvalidaException {
        try {
            return UriComponentsBuilder.fromHttpUrl(Objects.requireNonNull(url)).toUriString();
        } catch (NullPointerException e) {
            throw new CrawlerException.URLInvalidaException("URL não informada!");
        }
    }
}
