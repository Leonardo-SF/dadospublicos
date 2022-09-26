package br.com.dadospublicos.crawler.sites.alsp.api;

import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.model.Despesa;
import br.com.dadospublicos.crawler.interfaces.API;
import br.com.dadospublicos.commons.utils.BigDecimalUtils;
import br.com.dadospublicos.commons.utils.MesRefUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Scope("prototype")
public class ApiPrestacaoDeContasSP extends PageFactory implements API<List<Despesa>> {

    private final String url;

    @FindBys({
            @FindBy(id = "porNaturezas"),
            @FindBy(tagName = "table"),
            @FindBy(tagName = "tbody"),
            @FindBy(tagName = "tr")
    })
    private List<WebElement> linhas;

    private ApiPrestacaoDeContasSP() {
        this.url = null;
    }

    public ApiPrestacaoDeContasSP(String url) {
        this.url = url;

        var webDriver = this.newWebDriver();
        webDriver.get(url);

        initElements(webDriver, this);
    }

    @Override
    public List<Despesa> extrairInformacao() throws CrawlerException {
        try {
            final var mesRef = this.getMesRef();

            if (linhas.isEmpty())
                return new ArrayList<>(0);

            return this.extrairDespesas(linhas, mesRef);
        } catch (NoSuchElementException e) {
            throw new CrawlerException.HtmlException("HTML divergente do esperado para a url " + url);
        }
    }

    private List<Despesa> extrairDespesas(List<WebElement> linhas, String mesRef) {
        var despesas = new ArrayList<Despesa>(linhas.size());

        linhas.forEach(linha -> {
            var colunas = linha.findElements(By.tagName("td"));
            var despesa = new Despesa();

            despesa.setDescriacao(colunas.get(0).getText().split("-")[1]);
            despesa.setValor(BigDecimalUtils.from(colunas.get(1).getText()));
            despesa.setMesRef(mesRef);

            despesas.add(despesa);
        });

        return despesas;
    }

    private String getMesRef() throws CrawlerException.URLInvalidaException {
        try {
            var parametros = url.split("\\?")[1].split("&");

            var mes = Arrays.stream(parametros).filter(p -> p.startsWith("mes=")).findFirst().orElseThrow();
            var ano = Arrays.stream(parametros).filter(p -> p.startsWith("ano=")).findFirst().orElseThrow();

            mes = mes.split("=")[1];
            ano = ano.split("=")[1];

            return MesRefUtils.getMesRef(mes, ano);
        } catch (Exception e) {
            throw new CrawlerException.URLInvalidaException("URL " + url + " inválida");
        }
    }

    private HtmlUnitDriver newWebDriver() {
        System.setProperty("webdriver.gecko.driver", "/tmp/geco");
        return new HtmlUnitDriver();
    }

}
