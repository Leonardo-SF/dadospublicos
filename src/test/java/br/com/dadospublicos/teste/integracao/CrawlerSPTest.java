package br.com.dadospublicos.teste.integracao;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.crawler.sites.almg.CrawlerDeputadosMG;
import br.com.dadospublicos.crawler.sites.alsp.CrawlerDeputadosSP;
import br.com.dadospublicos.model.politico.Politico;
import br.com.dadospublicos.sites.Navegador;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class CrawlerSPTest {

    @Autowired
    private CrawlerDeputadosMG crawlerDeputadosMG;

    @SpyBean
    private Navegador navegador;

    @Test
    void buscarPorMandatoTest() throws CrawlerException, ArquivoException {
        assertEquals(77, crawlerDeputadosMG.buscarPorMandato().size());
    }

    @Test
    void buscarInformacoesGeraisTest() throws CrawlerException, ArquivoException {
        var politicos = crawlerDeputadosMG.buscarInformacoesGerais(Collections.singletonList("https://www.almg.gov.br/deputados/conheca_deputados/deputados-info.html?idDep=7752&leg=19"));
        assertEquals(1, politicos.size());
        assertEquals("Alencar da Silveira Jr.", politicos.get(0).getNomeAbreviado());
    }

    @Test
    void buscarDespesasTest() throws CrawlerException, ArquivoException {
        var politico = new Politico().setMatricula("7752");
        var dataReferencia = LocalDate.parse("2021-01-01");

        var politicos = crawlerDeputadosMG.buscarDespesas(Collections.singletonList(politico), dataReferencia);
        assertTrue(politicos.get(0).getDespesas().size() > 0);
    }

    @Test
    void buscarServidoresPorDeputadoTest() throws CrawlerException, ArquivoException {
        try {
            crawlerDeputadosMG.buscarInformacoesGabinete(new ArrayList<>(0));
        } catch (CrawlerException e) {
            assertTrue(e instanceof CrawlerException.OperacaoNaoSuportadaException);
        }
    }

}