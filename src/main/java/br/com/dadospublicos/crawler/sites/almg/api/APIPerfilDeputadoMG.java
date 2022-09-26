package br.com.dadospublicos.crawler.sites.almg.api;

import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.model.politico.Politico;
import br.com.dadospublicos.crawler.interfaces.API;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static br.com.dadospublicos.commons.exception.SafeRunner.loggingErrors;

@Component
@Scope("prototype")
public class APIPerfilDeputadoMG extends PageFactory implements API<Politico> {

    private final String url;

    private final WebDriver webDriver;

    @FindBy(xpath = "//div[@class='grid_18 suffix_0_5']//h2")
    private WebElement nomeAbreviado;

    @FindBy(xpath = "//div[@class='nome-deputadoInfo']//h3")
    private WebElement nome;

    @FindBy(xpath = "//div[@class='box-DeputadosContatos']//span[@class='DeputadosContatos-Right'][1]")
    private WebElement naturalidade;

    @FindBy(xpath = "//div[@class='box-DeputadosContatos']//span[@class='DeputadosContatos-Right'][2]")
    private WebElement nascimento;

    @FindBy(xpath = "//div[@class='box-DeputadosContatos']//span[@class='DeputadosContatos-Right'][3]")
    private WebElement sala;

    @FindBy(xpath = "//span[text()='Telefones:']//..//span[@class='DeputadosContatos-Right'][1]")
    private WebElement telefone;

    @FindBy(xpath = "//span[text()='Email:']//..//span[@class='DeputadosContatos-Right2'][1]")
    private WebElement email;

    private APIPerfilDeputadoMG() {
        this.url = null;
        this.webDriver = null;
    }

    public APIPerfilDeputadoMG(String url) {
        this.url = url;

        this.webDriver = newWebDriver();
        webDriver.get(url);

        initElements(webDriver, this);
    }

    @Override
    public Politico extrairInformacao() throws CrawlerException {
        try {
            return this.criarAgente();
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            throw new CrawlerException.HtmlException("");
        }
    }

    public Politico criarAgente() throws CrawlerException {
        var matricula = this.extrairMatricula();

        var agente = new Politico();
        agente.setMatricula(matricula);
        agente.setNome(this.extrairNome());
        agente.setNomeAbreviado(this.extrairNomeAbreviado());
        agente.setDataAniversario(this.extrair(nascimento));
        agente.setTelefone(this.extrair(telefone));
        agente.setEmail(this.extrair(email));
        agente.setSala(this.extrair(sala));
        agente.setPartido(this.extrairPartido());
        agente.setBiografia(this.extrairBiografia(matricula));

        return agente;
    }

    public String extrairNomeAbreviado() {
        return nomeAbreviado.getText().split("\\|")[0].trim();
    }

    public String extrairNome() {
        return nome.getText().split("\\(")[0];
    }

    public String extrairPartido() {
        return nome.getText().split("\\(")[1].replace(")", "").trim();
    }

    public String extrairMatricula() throws CrawlerException.URLInvalidaException {
        return this.extrairParametroDaUrl("idDep=");
    }

    public String extrairLegislatura() throws CrawlerException.URLInvalidaException {
        return this.extrairParametroDaUrl("leg=");
    }

    public String extrairParametroDaUrl(String parametro) throws CrawlerException.URLInvalidaException {
        try {
            var parametros = url.split("\\?")[1].split("&");

            var matricula = Arrays.stream(parametros)
                    .filter(p -> p.contains(parametro))
                    .findFirst()
                    .orElseThrow();

            return matricula.split("=")[1];
        } catch (Exception e) {
            throw new CrawlerException.URLInvalidaException(String.format("URL %s inválida por não possuir matricula!", url));
        }
    }

    public String extrairBiografia(String matricula) {
        try {
            var legislatura = extrairLegislatura();
            var linkBiografia = String.format(
                    "https://www.almg.gov.br/deputados/biografia/index.html?idDep=%s&leg=%s", matricula, legislatura
            );

            webDriver.get(linkBiografia);
            return webDriver.findElement(By.className("texto-biografia")).findElement(By.xpath("./p[2]")).getText();
        } catch (Exception e) {
            return null;
        }
    }

//    private String extrair(WebElement webElement) {
//        try {
//            return webElement.getText();
//        } catch (NoSuchElementException e) {
//            return null;
//        }
//    }

    private String extrair(WebElement webElement) {
        return loggingErrors(webElement::getText);
    }

    private HtmlUnitDriver newWebDriver() {
        return new HtmlUnitDriver();
    }
}
