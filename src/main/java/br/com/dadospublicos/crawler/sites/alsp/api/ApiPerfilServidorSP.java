package br.com.dadospublicos.crawler.sites.alsp.api;

import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.model.politico.Politico;
import br.com.dadospublicos.model.Cargo;
import br.com.dadospublicos.crawler.interfaces.API;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class ApiPerfilServidorSP extends PageFactory implements API<Politico> {

    private final String url;

    @FindBy(xpath = "//div[@class='quadro_conteudo']//p[2]")
    private WebElement nome;

    @FindBy(xpath = "//fieldset[@class='quadro_dados_funcionario'][1]//table//tbody//tr")
    private List<WebElement> historicoDeCargos;

    @FindBy(xpath = "//fieldset[@class='quadro_dados_funcionario'][2]//table//tbody//tr")
    private List<WebElement> historicoDeGabinetes;

    private ApiPerfilServidorSP() {
        this.url = null;
    }

    public ApiPerfilServidorSP(String url) {
        this.url = url;

        var webDriver = this.newWebDriver();
        webDriver.get(url);

        initElements(webDriver, this);
    }

    @Override
    public Politico extrairInformacao() throws CrawlerException {
        try {
            return this.criarAgente();
        } catch (NoSuchElementException e) {
            throw new CrawlerException.HtmlException("HTML divergente do esperado para a url " + url);
        }
    }

    public Politico criarAgente() {
        var agente = new Politico();
        agente.setNome(nome.getText());
        setCargo(agente);
        //setGabinetes

        return agente;
    }

    public void setCargo(Politico politico) {
        var cargos = new ArrayList<Cargo>();

        historicoDeCargos.forEach(cargoSite -> {
            var cargo = new Cargo();
            var colunas = cargoSite.findElements(By.tagName("td"));

            cargo.setNome(colunas.get(0).getText());

            var dataInicial = colunas.get(1).getText();
            cargo.setDataInicio(LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            var dataFinal = colunas.get(2).getText();
            if (dataFinal != null && !dataFinal.isEmpty())
                cargo.setDataFim(LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            cargos.add(cargo);
        });

        politico.setCargos(cargos);
    }

    private HtmlUnitDriver newWebDriver() {
        System.setProperty("webdriver.gecko.driver", "/tmp/geco");
        return new HtmlUnitDriver();
    }

}
