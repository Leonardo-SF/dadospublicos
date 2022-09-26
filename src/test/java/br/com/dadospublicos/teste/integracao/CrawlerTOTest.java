package br.com.dadospublicos.teste.integracao;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.crawler.sites.alsp.CrawlerDeputadosSP;
import br.com.dadospublicos.crawler.sites.alto.CrawlerDeputadosTO;
import br.com.dadospublicos.model.politico.Politico;
import br.com.dadospublicos.sites.Navegador;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class CrawlerTOTest {

    @Autowired
    private CrawlerDeputadosTO crawlerDeputadosTO;

    @SpyBean
    private Navegador navegador;

    @Test
    void buscarPorMandatoTest() throws CrawlerException, ArquivoException {
        assertEquals(24, crawlerDeputadosTO.buscarPorMandato().size());
    }

    @Test
    void buscarInformacoesGeraisTest() throws CrawlerException, ArquivoException {
        var politicos = crawlerDeputadosTO.buscarInformacoesGerais(Collections.singletonList("https://al.to.leg.br/perfil/parlamentar/antonio+andrade/1890"));
        assertEquals(1, politicos.size());
        assertEquals("Adalberto Freitas", politicos.get(0).getNome());
    }

    @Test
    void buscarDespesasTest() throws CrawlerException, ArquivoException {
        var politico = new Politico().setMatricula("300605");
        var dataReferencia = LocalDate.parse("2021-01-01");

        var politicos = crawlerDeputadosTO.buscarDespesas(Collections.singletonList(politico), dataReferencia);
        assertTrue(politicos.get(0).getDespesas().size() > 0);
    }

    @Test
    void buscarServidoresPorDeputadoTest() throws CrawlerException, ArquivoException {
        when(navegador.get("https://www.al.sp.gov.br/servidor/lista/?idUA=20377&tpSituacao=T"))
                .thenReturn(List.of("https://www.al.sp.gov.br/servidor/?matricula=29387"));

        var politicos = crawlerDeputadosTO.buscarInformacoesGabinete(new ArrayList<>(0));
    }

}