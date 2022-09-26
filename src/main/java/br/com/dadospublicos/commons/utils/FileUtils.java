package br.com.dadospublicos.commons.utils;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.commons.exception.SafeRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.file.Files;

import static br.com.dadospublicos.commons.exception.SafeRunner.loggingErrors;

public class FileUtils {

    public static File download(String url) throws IOException {
        var uri = SafeRunner.run(() -> UriComponentsBuilder.fromHttpUrl(url).toUriString())
                .orElseThrowsAs(IllegalArgumentException.class, new CrawlerException.URLInvalidaException())
                .get();

        var arquivo = criarTemporario();

        try (var channel = Channels.newChannel(new URL(uri).openStream());
             var outputStream = new FileOutputStream(arquivo)) {
            outputStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
            return arquivo;
        }
    }

    public static File criarTemporario() {
        return SafeRunner.loggingErrors(() -> criarTemporario("temp"));
    }

    public static File criarTemporario(String prefixo) throws ArquivoException.PrefixoInvalidoException {
        var arquivo = SafeRunner.run(() -> File.createTempFile(prefixo, null))
                .orElseThrowsAs(IllegalArgumentException.class, e -> {
                    throw new ArquivoException.PrefixoInvalidoException(e.getMessage());
                })
                .orElseThrow();

        arquivo.deleteOnExit();
        return arquivo;
    }

    public static void excluir(File file) {
        loggingErrors(() -> Files.delete(file.toPath()));
    }

}
