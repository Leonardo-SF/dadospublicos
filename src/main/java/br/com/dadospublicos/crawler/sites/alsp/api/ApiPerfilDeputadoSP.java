package br.com.dadospublicos.crawler.sites.alsp.api;

import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.model.politico.Politico;
import br.com.dadospublicos.crawler.interfaces.API;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class ApiPerfilDeputadoSP extends PageFactory implements API<Politico> {

    private final String url;

    @FindBy(xpath = "//*[text()='Quadro de funcionários do gabinete']")
    private WebElement linkServidores;

    @FindBy(tagName = "h2")
    private WebElement nome;

    @FindBy(css = ".col-md-12:nth-of-type(2)")
    private WebElement biografia;

    @FindBys({
            @FindBy(id = "infoGeral"),
            @FindBy(className = "form-group")
    })
    private List<WebElement> informacoesGerais;

    private ApiPerfilDeputadoSP() {
        this.url = null;
    }

    public ApiPerfilDeputadoSP(String url) {
        this.url = url;

        var webDriver = this.newWebDriver();
        webDriver.get(url);

        initElements(webDriver, this);
    }

    @Override
    public Politico extrairInformacao() throws CrawlerException {
        try {
            if (informacoesGerais.isEmpty())
                throw new ArrayIndexOutOfBoundsException();

            return this.criarAgente();
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException e) {
            throw new CrawlerException.HtmlException("HTML divergente do esperado para a url " + url);
        }
    }

    private String extrairMatricula() throws CrawlerException.URLInvalidaException {
        if (!url.contains("matricula="))
            throw new CrawlerException.URLInvalidaException(String.format("URL %s inválida por não possuir matricula!", url));

        return url.substring(url.indexOf("matricula=")).split("=")[1];
    }

    private Integer getUA() {
        var parametros = linkServidores.getAttribute("href").split("\\?")[1];
        return Integer.parseInt(parametros.split("=")[1]);
    }

    private Politico criarAgente() throws CrawlerException.URLInvalidaException {
        var matricula = extrairMatricula();

        var deputado = new Politico();

        deputado.setMatricula(matricula);
        deputado.setNome(nome.getText());
        deputado.setBiografia(biografia.getText().replaceFirst("Biografia", ""));
        deputado.setIdUA(getUA());
        setInformacoesGerais(deputado);

        return deputado;
    }

    private void setInformacoesGerais(Politico deputado) {
        informacoesGerais.forEach(informacao -> {
            var descricao = informacao.findElement(By.tagName("label")).getText();
            var valor = informacao.findElement(By.className("form-control")).getAttribute("value");

            if ("partido".equalsIgnoreCase(descricao))
                deputado.setPartido(valor);

            if ("base eleitoral".equalsIgnoreCase(descricao))
                deputado.setBaseEleitoral(valor);

            if ("e-mail".equalsIgnoreCase(descricao))
                deputado.setEmail(valor);

            if ("area de atuacao".equalsIgnoreCase(StringUtils.stripAccents(descricao)))
                deputado.setAreaDeAtuacao(valor);

            if ("telefone".equalsIgnoreCase(descricao))
                deputado.setTelefone(valor);

            if ("sala / andar".equalsIgnoreCase(descricao))
                deputado.setSala(valor);

            if ("veiculo".equalsIgnoreCase(StringUtils.stripAccents(descricao)))
                deputado.setVeiculo(valor);

            if ("aniversario".equalsIgnoreCase(StringUtils.stripAccents(descricao)))
                deputado.setDataAniversario(valor);
        });
    }

    public HtmlUnitDriver newWebDriver() {
        System.setProperty("webdriver.gecko.driver", "/tmp/geco");
        return new HtmlUnitDriver();
    }

}
