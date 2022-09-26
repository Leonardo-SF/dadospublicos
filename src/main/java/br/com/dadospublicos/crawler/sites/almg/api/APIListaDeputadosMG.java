package br.com.dadospublicos.crawler.sites.almg.api;

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
public class APIListaDeputadosMG extends PageFactory implements API<List<Politico>> {

    @FindBy(xpath = "//ul[@id='deputados_view-imagem']//li/div//a")
    private List<WebElement> elementosHref;

    private APIListaDeputadosMG() {}

    public APIListaDeputadosMG(String url) {
        var webDriver = newWebDriver();
        webDriver.get(url);

        initElements(webDriver, this);
    }

    @Override
    public List<Politico> extrairInformacao() {
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
