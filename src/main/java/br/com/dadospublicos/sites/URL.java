package br.com.dadospublicos.sites;

import br.com.dadospublicos.crawler.sites.almg.api.APIListaDeputadosMG;
import br.com.dadospublicos.crawler.sites.almg.api.APIPerfilDeputadoMG;
import br.com.dadospublicos.crawler.sites.almg.api.APIPrestacaoDeContasMG;
import br.com.dadospublicos.crawler.sites.alrj.api.APIPrestacaoDeContasRJ;
import br.com.dadospublicos.crawler.sites.alsp.api.*;
import br.com.dadospublicos.crawler.sites.alto.api.APIListaDeputadosTO;
import br.com.dadospublicos.crawler.sites.alto.api.APIPerfilDeputadosTO;
import br.com.dadospublicos.crawler.sites.alto.api.APIPrestacaoDeContasTO;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class URL {

    protected static final Map<Pattern, Class<?>> MAPA;

    private static final Pattern URL_LISTA_DEPUTADOS_SP = Pattern.compile("https://www\\.al\\.sp\\.gov\\.br/deputado/lista");
    private static final Pattern URL_PERFIL_DEPUTADOS_SP = Pattern.compile("^https://www\\.al\\.sp\\.gov\\.br/deputado/\\?matricula=([0-9]*$)");
    private static final Pattern URL_GABINETE_DEPUTADOS_SP = Pattern.compile("^https://www\\.al\\.sp\\.gov\\.br/servidor/lista/\\?idUA=([0-9]*)&tpSituacao=T$");
    private static final Pattern URL_DESPESAS_DEPUTADOS_SP = Pattern.compile("^https://www\\.al\\.sp\\.gov\\.br/deputado/contas/\\?");
    private static final Pattern URL_PERFIL_SERVIDORES_SP = Pattern.compile("^https://www\\.al\\.sp\\.gov\\.br/servidor/\\?matricula=([0-9]*$)");

    private static final Pattern URL_LISTA_DEPUTADOS_MG = Pattern.compile("^https://www\\.almg\\.gov\\.br/deputados/conheca_deputados/index\\.html$");
    private static final Pattern URL_PERFIL_DEPUTADOS_MG = Pattern.compile("^https://www\\.almg\\.gov\\.br/deputados/conheca_deputados/deputados-info\\.html\\?idDep=([0-9]*)&leg=([0-9]{2}$)");
    private static final Pattern URL_DESPESAS_DEPUTADOS_MG = Pattern.compile("^http://dadosabertos\\.almg\\.gov\\.br/ws/prestacao_contas/verbas_indenizatorias/deputados/([0-9]*)/[0-9]{4}/[0-9]{1,2}\\?formato=json");

    private static final Pattern URL_DESPESAS_DEPUTADOS_RJ = Pattern.compile("^https://docigp\\.alerj\\.rj\\.gov\\.br/api/v1/congressmen/[0-9]{1,2}/budgets\\?*");

    private static final Pattern URL_LISTA_DEPUTADOS_TO = Pattern.compile("^https://al\\.to\\.leg\\.br/perfil$");
    private static final Pattern URL_PERFIL_DEPUTADOS_TO = Pattern.compile("^https://al\\.to\\.leg\\.br/perfil/parlamentar/*");
    private static final Pattern URL_DESPESAS_DEPUTADOS_TO = Pattern.compile("^https://al\\.to\\.leg\\.br/transparencia/baixar\\?arquivo=transparencia_ano_([0-9]){4}_[0-9]{2}_([0-9]*)\\.PDF$");


    static {
        MAPA = new HashMap<>();
        MAPA.put(URL.URL_LISTA_DEPUTADOS_SP, ApiListaDeputadosSP.class);
        MAPA.put(URL.URL_PERFIL_DEPUTADOS_SP, ApiPerfilDeputadoSP.class);
        MAPA.put(URL.URL_DESPESAS_DEPUTADOS_SP, ApiPrestacaoDeContasSP.class);
        MAPA.put(URL.URL_GABINETE_DEPUTADOS_SP, ApiGabineteDeputadoSP.class);
        MAPA.put(URL.URL_PERFIL_SERVIDORES_SP, ApiPerfilServidorSP.class);

        MAPA.put(URL.URL_LISTA_DEPUTADOS_MG, APIListaDeputadosMG.class);
        MAPA.put(URL.URL_PERFIL_DEPUTADOS_MG, APIPerfilDeputadoMG.class);
        MAPA.put(URL.URL_DESPESAS_DEPUTADOS_MG, APIPrestacaoDeContasMG.class);

        MAPA.put(URL.URL_DESPESAS_DEPUTADOS_RJ, APIPrestacaoDeContasRJ.class);

        MAPA.put(URL_LISTA_DEPUTADOS_TO, APIListaDeputadosTO.class);
        MAPA.put(URL_PERFIL_DEPUTADOS_TO, APIPerfilDeputadosTO.class);
        MAPA.put(URL_DESPESAS_DEPUTADOS_TO, APIPrestacaoDeContasTO.class);
    }

}
