package br.com.dadospublicos.crawler.sites.alto.api;

import br.com.dadospublicos.crawler.sites.alto.dto.DeputadoALTODTO;
import br.com.dadospublicos.crawler.sites.alto.dto.DespesaItemALTODTO;
import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.commons.exception.SafeRunner;
import br.com.dadospublicos.crawler.interfaces.API;
import br.com.dadospublicos.commons.utils.BigDecimalUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static br.com.dadospublicos.commons.exception.SafeRunner.loggingErrors;
import static br.com.dadospublicos.commons.utils.FileUtils.download;
import static br.com.dadospublicos.commons.utils.FileUtils.excluir;

@Component
@Scope("prototype")
public class APIPrestacaoDeContasTO implements API<List<DeputadoALTODTO>> {
    private final String url;
    private final Map<String, DeputadoALTODTO> deputados = new HashMap<>();
    private final Map<Coluna, Consumer<String>> consumidor;
    private Coluna coluna = Coluna.NE;
    private DeputadoALTODTO deputadoTemp = null;
    private final String ano;

    public APIPrestacaoDeContasTO(String url) {
        this.url = url;
        this.ano = extrairAno();
        this.consumidor = Map.of(
                Coluna.NE, s -> coluna = Coluna.CNPJ,
                Coluna.CNPJ, salvarCpfCnpj,
                Coluna.FAVORECIDO, salvarFavorecido,
                Coluna.NL, s -> coluna = Coluna.DATA_EMISSAO,
                Coluna.DATA_EMISSAO, salvarData,
                Coluna.HISTORICO, salvarHistorico,
                Coluna.SUBELEMENTO, salvarSubElemento,
                Coluna.VALOR, salvarValor);
    }

    @Override
    public List<DeputadoALTODTO> extrairInformacao() throws CrawlerException, ArquivoException {
        var ref = new Object() {
            File arquivo = null;
        };

        try {
            SafeRunner.run(() -> ref.arquivo = download(url))
                    .flatMap(this::carregarPDF)
                    .flatMap(this::extrairTexto)
                    .flatMap(this::quebrarLinhas)
                    .andThen(this::processarBlocos);

            return this.getListaFiltrada();
        } catch (IOException e) {
            throw new CrawlerException.RecursoInvalidoException("Não foi possível extrair linhas do pdf");
        } finally {
            excluir(ref.arquivo);
        }
    }

    private PDDocument carregarPDF(File arquivo) throws ArquivoException.ArquivoCorrompidoException {
        try {
            return PDDocument.load(arquivo);
        } catch (IOException e) {
            throw new ArquivoException.ArquivoCorrompidoException();
        }
    }

    private String extrairTexto(PDDocument pdf) throws IOException {
        return new PDFTextStripper().getText(pdf);
    }

    private String[] quebrarLinhas(String texto) {
        return texto.split("\n");
    }

    private List<DeputadoALTODTO> getListaFiltrada() {
        var lista = new ArrayList<DeputadoALTODTO>();

        deputados.entrySet()
                .removeIf(key -> key.getKey().length() > 11);

        deputados.values()
                .forEach(deputado -> {
                    deputado.getDespesas().removeIf(i -> i.getSubElemento() == null);
                    lista.add(deputado);
                });

        return lista;
    }

    private String extrairAno() {
        return url.split("_")[2];
    }

    private void processarBlocos(String[] linhas) {
        var blocoValido = false;

        for (var linha : linhas) {
            if (!blocoValido && inicioBloco(linha)) {
                blocoValido = true;
                deputadoTemp = new DeputadoALTODTO();
            }

            if (blocoValido) {
                if (fimBloco(linha)) {
                    blocoValido = false;
                    finalizarBloco();
                    continue;
                }

                consumirLinha(linha);
            }
        }
    }

    private void consumirLinha(String linha) {
        var palavras = linha.split(" ");

        for (var palavra : palavras)
            this.consumidor.get(coluna).accept(palavra);
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final Consumer<String> salvarCpfCnpj = (String cpfCnpj) -> {
        cpfCnpj = cpfCnpj.replaceAll("[^0-9]", "");
        deputadoTemp.setCpf(cpfCnpj);

        coluna = Coluna.FAVORECIDO;
    };

    @SuppressWarnings("FieldCanBeLocal")
    private final Consumer<String> salvarData = (String data) -> {
        var item = new DespesaItemALTODTO();

        loggingErrors(() ->
                item.setDataEmissao(LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

        deputadoTemp.getDespesas().add(item);
        coluna = Coluna.HISTORICO;
    };

    @SuppressWarnings("FieldCanBeLocal")
    private final Consumer<String> salvarFavorecido = (String nome) -> {
        if (isNL(nome)) {
            coluna = Coluna.DATA_EMISSAO;
        } else {
            if (deputadoTemp.getNomeDeputado() == null)
                deputadoTemp.setNomeDeputado(nome);
            else
                deputadoTemp.setNomeDeputado(deputadoTemp.getNomeDeputado() + " " + nome);
        }
    };

    @SuppressWarnings("FieldCanBeLocal")
    private final Consumer<String> salvarHistorico = (String historico) -> {
        if (isND(historico)) {
            coluna = Coluna.SUBELEMENTO;
        } else {
            var ultimaDespesa = deputadoTemp.getDespesas().get(deputadoTemp.getDespesas().size() - 1);

            if (ultimaDespesa.getDescricao() == null) {
                ultimaDespesa.setDescricao(historico);
            } else {
                var descricao = ultimaDespesa.getDescricao() + " " + historico;
                ultimaDespesa.setDescricao(descricao);
            }
        }
    };

    @SuppressWarnings("FieldCanBeLocal")
    private final Consumer<String> salvarSubElemento = (String subElemento) -> {
        var ultimaDespesa = deputadoTemp.getDespesas().get(deputadoTemp.getDespesas().size() - 1);

        if (ultimaDespesa.getSubElemento() == null) {
            ultimaDespesa.setSubElemento(subElemento);
        } else {
            ultimaDespesa.setSubElemento(ultimaDespesa.getSubElemento() + subElemento);
        }

        if (ultimaDespesa.getSubElemento().length() == 2) {
            coluna = Coluna.VALOR;
        }
    };

    @SuppressWarnings("FieldCanBeLocal")
    private final Consumer<String> salvarValor = (String valor) -> {
        deputadoTemp.getDespesas().get(deputadoTemp.getDespesas().size() - 1).setValor(BigDecimalUtils.from(valor));
        coluna = Coluna.NL;
    };

    private boolean inicioBloco(String linha) {
        return isNE(linha);
    }

    private boolean fimBloco(String linha) {
        return linha.trim().equals("");
    }

    private void finalizarBloco() {
        if (!this.deputados.containsKey(deputadoTemp.getCpf())) {
            deputados.put(deputadoTemp.getCpf(), new DeputadoALTODTO());
        }

        var deputado = deputados.get(deputadoTemp.getCpf());
        deputado.setCpf(deputadoTemp.getCpf());
        deputado.setNomeDeputado(deputadoTemp.getNomeDeputado());
        deputado.getDespesas().addAll(deputadoTemp.getDespesas());

        coluna = Coluna.NE;
        deputadoTemp = null;
    }

    private boolean isNE(String string) {
        return string.startsWith(ano + "NE");
    }

    private boolean isNL(String string) {
        return string.startsWith(ano + "NL");
    }

    private boolean isND(String string) {
        return string.startsWith("33") && string.length() == 6 && NumberUtils.isDigits(string);
    }

    private enum Coluna {
        NE, CNPJ, FAVORECIDO, NL, DATA_EMISSAO, HISTORICO, SUBELEMENTO, VALOR
    }

}
