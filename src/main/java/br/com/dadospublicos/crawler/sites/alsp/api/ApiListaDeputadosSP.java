package br.com.dadospublicos.crawler.sites.alsp.api;

import br.com.dadospublicos.crawler.interfaces.API;
import br.com.dadospublicos.model.politico.Politico;
import org.openqa.selenium.By;
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
public class ApiListaDeputadosSP extends PageFactory implements API<List<Politico>> {

    @FindBy(xpath = "//th[@class='tabela-deputados-completa'][1]//..//..//..//tbody//tr")
    private List<WebElement> linhas;

    private ApiListaDeputadosSP() { }

    public ApiListaDeputadosSP(String url) {
        var webDriver = newWebDriver();
        webDriver.get(url);

        initElements(webDriver, this);
    }

    @Override
    public List<Politico> extrairInformacao() {
        var politicos = new ArrayList<Politico>(linhas.size());

        linhas.forEach(linha -> {
            var politico = new Politico();
            politico.setLinkPerfil(extrairLinkDoPerfil(linha));
            politicos.add(politico);
        });

        return politicos;
    }

    private String extrairLinkDoPerfil(WebElement linha) {
        var coluna = linha.findElements(By.tagName("td")).get(0);
        return coluna.findElement(By.tagName("a")).getAttribute("href");
    }

    public HtmlUnitDriver newWebDriver() {
        return new HtmlUnitDriver();
    }

}
