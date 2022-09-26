package br.com.dadospublicos;

import br.com.dadospublicos.crawler.sites.alsp.CrawlerDeputadosSP;
import br.com.dadospublicos.crawler.sites.alto.dto.DeputadoALTODTO;
import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.commons.exception.SafeRunner;
import br.com.dadospublicos.sites.Navegador;
import br.com.dadospublicos.crawler.sites.alto.api.APIListaDeputadosTO;
import br.com.dadospublicos.crawler.sites.alto.api.APIPerfilDeputadosTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class DadospublicosApplicationTests {

    @Autowired
    private CrawlerDeputadosSP crawlerSP;

    @Autowired
    private Navegador navegador;

//    @Autowired
//    private CrawlerFacade crawlerFacade;

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() throws CrawlerException, ArquivoException {
//		var agentes = crawlerSP.buscarDeputadosSP();
//		agentes.forEach(a -> System.out.println(a.toString()));

//		crawlerFacade.atualizaDeputados();
//		crawlerFacade.atualizaDespesasPorMatriculaEData("300605", LocalDate.parse("2021-01-01"));
//		navegador.get("https://www.almg.gov.br/deputados/conheca_deputados/index.html");
//		navegador.get("https://www.almg.gov.br/deputados/conheca_deputados/deputados-info.html?idDep=7752&leg=19");
//		navegador.get("https://www.almg.gov.br/deputados/conheca_deputados/deputados-info.html?idDep=15250&leg=17");
//		navegador.get("http://dadosabertos.almg.gov.br/ws/prestacao_contas/verbas_indenizatorias/deputados/12195/2021/1?formato=json");
        navegador.get("https://docigp.alerj.rj.gov.br/api/v1/congressmen/2/budgets?query={\"pagination\":{\"per_page\":" + Integer.MAX_VALUE + ",\"current_page\":1}}");
//		System.out.println(Integer.MAX_VALUE);


//		navegador.get("https://www.al.sp.gov.br/deputado/?matricula=300605");
//		navegador.get("https://www.al.sp.gov.br/deputado/contas/?matricula=300605&mes=1&ano=2021&tipo=naturezas");
//		navegador.get("https://www.al.sp.gov.br/servidor/?matricula=29387");

//		crawlerSP.atualizarInformacoesDosDeputadosEstaduais();

        /*
         * Site site = new SiteALSP();
         * Pagina pagina = site.getPagina("");
         * pagina.extrairInformacao();
         * */
    }

    @Test
    public void performanceTest() throws Throwable {
//        var a = SafeRunner.run(() -> criarTemporario("12"))
//                .orElseThrowAs(NumberFormatException.class, new PaginaException.URLInvalidaException("url inválida"))
//                .orElseThrow();

//        var a = SafeRunner.run(() -> criarTemporario("12"))
//                .orElseThrowAs(IllegalArgumentException.class, new PaginaException.URLInvalidaException("THROW 1"))
//                .orElseThrow();

//        var a = SafeRunner.run(() -> criarTemporario("12"))
//                .orElseThrowsAs(IllegalArgumentException.class, e -> {
//                    throw new PaginaException.URLInvalidaException("THROW 2 " + e.getMessage());
//                })
//                .orElseThrow();

//        var a = SafeRunner.loggingErrors(() -> criarTemporario("123"));

//        SafeRunner.run(() -> "0223avc")
//                .flatMap(String::toUpperCase)
//                .flatMap(Long::valueOf)
//                .flatMap(v -> v.toString() +"nil")
//                .get(v -> {
//                    System.out.println(v);
//                    return null;
//                });

//        var url = "https://al.to.leg.br/transparencia/baixar?arquivo=transparencia_ano_2021_2_53823.PDF";
//        var linhas = run(() -> download(url))
//                .orElseThrowsAs(IllegalArgumentException.class, new PaginaException.URLInvalidaException("URL Inválida"))
//                .flatMap(PDDocument::load)
//                .flatMap(pdf -> new PDFTextStripper().getText(pdf))
//                .flatMap(texto -> texto.split("\n"))
//                .get();

        teste();

//        System.out.println("a");
//        context.getBean(PaginaPrestacaoDeContasTO.class, "").extrairInformacao();
//        String a = "https://al.to.leg.br/transparencia/baixar?arquivo=transparencia_ano_2018_2_46556.PDF";
//        FileUtils.download("https://al.to.leg.br/transparencia/baixar?arquivo=transparencia_ano_2018_2_46556.PDF");

        System.out.println("a");
    }

//    private void teste() throws IOException {
//        var url = "https://al.to.leg.br/transparencia/baixar?arquivo=transparencia_ano_2021_2_53823.PDF";
//        try {
//            context.getBean(PaginaPrestacaoDeContasTO.class, url).extrairInformacao();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void teste() throws IOException, CrawlerException, ArquivoException, InterruptedException {
        var perfis = context.getBean(APIListaDeputadosTO.class, "https://al.to.leg.br/perfil").extrairInformacao();
//        var url = "https://al.to.leg.br/perfil/parlamentar/amalia-santana/2252";


        var blockingQueue = new LinkedBlockingDeque<DeputadoALTODTO>(perfis.size());

        var deputados = new ArrayList<DeputadoALTODTO>(perfis.size());
        ExecutorService es = Executors.newCachedThreadPool();
        for (var perfil : perfis) {
            var t = new Thread(() -> {
                SafeRunner.loggingErrors(() -> {
                    var deputado = context.getBean(APIPerfilDeputadosTO.class, perfil).extrairInformacao();
                    //            deputados.add(deputado);
                    blockingQueue.add(deputado);
                });
            });
            es.execute(t);
        }

        es.shutdown();

        try {
            if (!es.awaitTermination(1, TimeUnit.SECONDS)) {
//                es.shutdownNow();
                es.shutdownNow();
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException ex) {
            es.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("a");
    }

    public boolean arquivoCerto(String[] linhas) throws IOException {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void factory() throws ClassNotFoundException, CrawlerException, ArquivoException {
        var r = SafeRunner
                .run(this::lanca)
                .fallback(this::fallback);


        System.out.println(r);
    }

    public Example fallback(Exception e) {
        System.out.println(e.getMessage());
        return new Example();
    }

    public Example lanca() {
        if (1 == 2)
            throw new NullPointerException("olá");

        return new Example();
    }

    private static class Example {
        String linha = null;
        List<String> erros = new ArrayList<>();
    }


}