package br.com.dadospublicos.crawler.sites.alto.api;

import br.com.dadospublicos.commons.exception.ArquivoException;
import br.com.dadospublicos.commons.exception.CrawlerException;
import br.com.dadospublicos.commons.exception.SafeRunner;
import br.com.dadospublicos.crawler.interfaces.API;
import br.com.dadospublicos.crawler.sites.alto.dto.DeputadoALTODTO;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Scope("prototype")
public class APIPerfilDeputadosTO extends PageFactory implements API<DeputadoALTODTO> {

    @FindBy(xpath = "//div[@class='profile-title']")
    private WebElement nome;

    @FindBy(xpath = "//div[@class='profile-content-header']//div[2]")
    private WebElement partido;

    @FindBy(xpath = "//div[@class='profile-content-header']//div[3]//a")
    private WebElement email;

    @FindBy(xpath = "//div[@class='profile-content-header']//div[5]//a")
    private WebElement telefone;

    @FindBy(xpath = "//div[@id='collapseBiografia']//div[@class='card-body']//div")
    private WebElement biografia;


    public APIPerfilDeputadosTO(String url) {
        var webDriver = newWebDriver();
        webDriver.get(url);

        initElements(webDriver, this);
    }

    @Override
    public DeputadoALTODTO extrairInformacao() throws CrawlerException, ArquivoException {
        try {
            return criarDeputado();
        } catch (NoSuchElementException e) {
            throw new CrawlerException.HtmlException("HTML divergente do esperado");
        }
    }


    public DeputadoALTODTO criarDeputado() {
        var deputado = new DeputadoALTODTO();
        deputado.setNomeDeputado(this.nome.getText());
        deputado.setPartido(this.partido.getText());
        deputado.setEmail(this.email.getText());
        deputado.setBiografia(this.biografia.getText());

        var nomeCompleto = SafeRunner.loggingErrors(() ->
                this.extrairNomeCompleto(deputado.getNomeDeputado(), deputado.getBiografia()));

        deputado.setNomeCompleto(nomeCompleto);

        return deputado;
    }

    public String extrairNomeCompleto(String nome, String biografia) {
        var primeiroNome = nome.split(" ")[0];

        int index = 0;
        if (!apelido(primeiroNome))
             index = biografia.indexOf(primeiroNome);

        var texto = biografia.substring(index).split(" ");
        var nomeCompleto = new StringBuilder();

        for (int i = 0; i < 20; i ++) {
            var string = texto[i];

           if (string.equals("é") || string.equals("nasceu") || string.contains("(") || string.contains("foi")) {
               break;
           }

           if (i == 0 && string.equalsIgnoreCase("biografia:")) {
               continue;
           }

           if (string.contains(",") || string.contains(".")) {
               string = string.replace(",", "")
                       .replace(".", "");
               nomeCompleto.append(string).append(" ");
               break;
           }

           nomeCompleto.append(string).append(" ");
        }
        return nomeCompleto.toString().trim();
    }

    public boolean apelido(String nome) {
        return nome.length() <= 3;
    }

    public HtmlUnitDriver newWebDriver() {
        return new HtmlUnitDriver();
    }
}
