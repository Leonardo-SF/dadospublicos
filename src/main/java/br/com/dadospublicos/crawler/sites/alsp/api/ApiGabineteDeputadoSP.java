package br.com.dadospublicos.crawler.sites.alsp.api;

import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.crawler.interfaces.API;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class ApiGabineteDeputadoSP extends PageFactory implements API<List<String>> {

    private final String url;

    @FindBys({
            @FindBy(className = "lotacao"),
            @FindBy(tagName = "tbody"),
            @FindBy(tagName = "a")
    })
    private List<WebElement> links;

    private ApiGabineteDeputadoSP() {
        this.url = null;
    }

    public ApiGabineteDeputadoSP(String url) {
        this.url = url;

        var webDriver = this.newWebDriver();
        webDriver.get(url);

        initElements(webDriver, this);
    }

    @Override
    public List<String> extrairInformacao() throws CrawlerException {
        try {
            if (links.isEmpty())
                return new ArrayList<>(0);

            return links.stream().map(link -> link.getAttribute("href")).collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            throw new CrawlerException.HtmlException("HTML divergente do esperado para a url " + url);
        }
    }

    private HtmlUnitDriver newWebDriver() {
        System.setProperty("webdriver.gecko.driver", "/tmp/geco");
        return new HtmlUnitDriver();
    }

}
