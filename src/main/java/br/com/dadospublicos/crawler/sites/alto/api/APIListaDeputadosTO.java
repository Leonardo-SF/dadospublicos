package br.com.dadospublicos.crawler.sites.alto.api;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.crawler.interfaces.API;
import br.com.dadospublicos.model.politico.Politico;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class APIListaDeputadosTO extends PageFactory implements API<List<Politico>> {

    @FindBy(xpath = "//div[@id='list-parlamentares']//a")
    private List<WebElement> elementosHref;

    public APIListaDeputadosTO(String url) {
        var webDriver = newWebDriver();
        webDriver.get(url);

        initElements(webDriver, this);
    }

    @Override
    public List<Politico> extrairInformacao() throws CrawlerException, ArquivoException {
        var politicos = new ArrayList<Politico>(elementosHref.size());
        elementosHref.forEach(href -> {
            var politico = new Politico();
            politico.setLinkPerfil(href.getAttribute("href"));
            politicos.add(politico);
        });
        return politicos;
    }

    public HtmlUnitDriver newWebDriver() {
        return new HtmlUnitDriver();
    }
}
