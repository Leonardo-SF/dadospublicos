package br.com.dadospublicos.crawler.sites.almg.api;

import br.com.dadospublicos.crawler.sites.almg.dto.DespesaALMGDTO;
import br.com.dadospublicos.crawler.sites.almg.dto.DespesaMGWrapper;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.model.Despesa;
import br.com.dadospublicos.crawler.interfaces.API;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
public class APIPrestacaoDeContasMG implements API<List<Despesa>> {

    private final String url;

    private APIPrestacaoDeContasMG() {
        this.url = null;
    }

    public APIPrestacaoDeContasMG(String url) {
        this.url = url;
    }

    @Override
    public List<Despesa> extrairInformacao() throws CrawlerException {
        var despesasDTO = WebClient.create(url)
                .get()
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new CrawlerException.ErroInternoException("Erro na comunicação com o servidor")))
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new CrawlerException.RequisicaoInvalidaException("Erro na requisição " + url)))
                .bodyToMono(DespesaMGWrapper.class)
                .map(DespesaMGWrapper::getList)
                .block();

        var despesas = new ArrayList<Despesa>();
        Objects.requireNonNull(despesasDTO, "DespesasWrapper não pode estar nulo!")
                .forEach(dto -> despesas.addAll(this.parse(dto)));

        return despesas;
    }

    private List<Despesa> parse(DespesaALMGDTO despesaDTO) {
        var despesas = new ArrayList<Despesa>(despesaDTO.getListaDetalheVerba().size());

        despesaDTO.getListaDetalheVerba().forEach(detalheDespesa -> {
            var despesa = new Despesa();
            despesa.setDescriacao(despesaDTO.getDescTipoDespesa());
            despesa.setValor(detalheDespesa.getValorReembolsado());
            despesa.setCnpjCpfFornecedor(detalheDespesa.getCpfCnpj());
            despesa.setNomeFornecedor(detalheDespesa.getNomeEmitente());
            despesas.add(despesa);
        });

        return despesas;
    }

}
