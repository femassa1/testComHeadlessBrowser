import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class mvc {
    protected static DesiredCapabilities capabilities;
    private static WebDriver driver;
    private static WebDriverWait wait;

    @Test
    public void testComHeadlessBrowser() throws InterruptedException, IOException {
        File file = new File("phantomjs");
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendario = Calendar.getInstance();


        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
        caps.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);

        driver = new PhantomJSDriver(caps);
        driver.manage().window().setSize(new Dimension(1920, 1080));

        try {

            System.out.println("Início do teste");

            //driver.get("https://qa.me.com.br/Default.asp?_OrigemID=1");
            driver.get("https://trunk.me.com.br/Default.asp");

            Thread.sleep(3000);

            //wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
            //driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
            trocarFrame();

            driver.findElement(By.name("LoginName")).clear();
            driver.findElement(By.name("LoginName")).sendKeys("B00LPA");

            driver.findElement(By.name("RAWSenha")).clear();
            //if(driver.getCurrentUrl().contains("qa"))
            //driver.findElement(By.name("RAWSenha")).sendKeys("qualidade@123");
            //else
            driver.findElement(By.name("RAWSenha")).sendKeys("trunk123");

            driver.findElement(By.cssSelector(".botaoLogin")).click();

            Thread.sleep(5000);

            if (driver.getCurrentUrl().contains("Timezone")) {
                System.out.println("TimezonePopup");
                screenshot();
                trocarFrame();
                try {
                    driver.findElement(By.id("btnAcceptTimeZone")).click();
                } catch (Exception e) {
                    trocarFrame();
                    driver.findElement(By.id("btnAcceptTimeZone")).click();
                }
                Thread.sleep(3000);
                driver.switchTo().defaultContent();
            }

            driver.findElement(By.cssSelector("a[href='DO/Request/Create.mvc']")).click();
            Thread.sleep(4000);

            driver.findElement(By.id("btnConfiguration")).click();
            Thread.sleep(3000);
            driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
            Thread.sleep(1000);
            driver.findElement(By.id("btnClearBOrgs")).click();
            Thread.sleep(1000);
            Select combo = new Select(driver.findElement(By.name("Categoria.Value")));
            combo.selectByValue("NB");

            driver.findElement(By.id("BOrgs_0__BorgDescription")).sendKeys("MC11 - MC11 - NAO PRODUTIVO");
            Thread.sleep(2000);
            driver.findElement(By.id("BOrgs_0__BorgDescription")).sendKeys(Keys.DOWN);
            driver.findElement(By.id("BOrgs_0__BorgDescription")).sendKeys(Keys.ENTER);

            driver.findElement(By.id("BOrgs_1__BorgDescription")).sendKeys("0007 - INTERNATIONAL SANTO AMARO (0007)");
            Thread.sleep(2000);
            driver.findElement(By.id("BOrgs_1__BorgDescription")).sendKeys(Keys.DOWN);
            driver.findElement(By.id("BOrgs_1__BorgDescription")).sendKeys(Keys.ENTER);

            driver.findElement(By.id("BOrgs_2__BorgDescription")).sendKeys("M100 - M100 - Centro Série 10");
            Thread.sleep(2000);
            driver.findElement(By.id("BOrgs_2__BorgDescription")).sendKeys(Keys.DOWN);
            driver.findElement(By.id("BOrgs_2__BorgDescription")).sendKeys(Keys.ENTER);

            driver.findElement(By.id("btnSave")).click();

            Thread.sleep(3000);

            //driver.switchTo().defaultContent();

            System.out.println(driver.getCurrentUrl());

            driver.findElement(By.xpath("//input[@placeholder='Buscar']")).sendKeys("GC96034010");

            driver.findElement(By.id("catalog-ativar")).click();

            Thread.sleep(1000);

            driver.findElement(By.cssSelector("input[id='Tipo'][type='radio'][value='" + "CodigoCliente" + "']")).click();

            driver.findElement(By.cssSelector("input[type='checkbox'][id='" + "BuscaExata" + "']")).click();

            driver.findElement(By.id("btnSearchSimple")).click();

            Thread.sleep(5000);

            driver.findElement(By.xpath("//button[@onclick='clickRemoveAll();']")).click();
            driver.findElement(By.xpath("//button[contains(.,'Sim')]")).click();

            Thread.sleep(5000);

            driver.findElement(By.cssSelector("span[class='awesome shopping-cart']")).click();

            Thread.sleep(2000);

            System.out.println("Item adicionado");

            driver.findElement(By.id("btnAvancar")).click();

            Thread.sleep(4000);

            Integer count = 0;
            while (driver.findElements(By.id("Observacao_Value")).size() == 0 && count < 10) {
                Thread.sleep(2000);
                count++;

                if (count == 8) {
                    System.out.println("Item adicionado");
                    screenshot();
                }
            }

            driver.findElement(By.id("Observacao_Value")).sendKeys("TESTE AUTOMAÇÃO - QA");

            driver.findElement(By.name("Attributes[0].valor")).sendKeys("TESTE AUTOMAÇÃO - QA");

            driver.findElement(By.id("btnAvancar")).click();

            Thread.sleep(5000);

            System.out.println("Comentários adicionados");

            combo = new Select(driver.findElement(By.name("Itens[0].GrupoCompras.Value")));
            combo.selectByValue("115");
            combo = new Select(driver.findElement(By.name("Itens[0].CategoriaContabil.Value")));
            combo.selectByValue("K");
            Thread.sleep(3000);

            driver.findElement(By.name("Itens[0].DataEsperadaComprador.Value")).click();

            driver.findElement(By.name("Itens[0].DataEsperadaComprador.Value")).sendKeys(formato.format(calendario.getTime()).replaceAll("/", ""));
            driver.findElement(By.name("Itens[0].BOrgs[0].BorgDescription")).click();
            Thread.sleep(1000);

            System.out.println("preenchendo atributos");

            driver.findElement(By.name("Itens[0].BOrgs[0].BorgDescription")).sendKeys(Keys.DOWN);
            driver.findElement(By.name("Itens[0].BOrgs[0].BorgDescription")).sendKeys(Keys.ENTER);
            combo = new Select(driver.findElement(By.name("Itens[0].LocalEntrega.Value")));
            combo.selectByIndex(1);
            driver.findElement(By.name("Itens[0].BOrgs[1].BorgDescription")).sendKeys("INTERNATIONAL SANTO AMARO (0007)");
            Thread.sleep(1000);
            driver.findElement(By.name("Itens[0].BOrgs[1].BorgDescription")).sendKeys(Keys.DOWN);
            driver.findElement(By.name("Itens[0].BOrgs[1].BorgDescription")).sendKeys(Keys.ENTER);
            driver.findElement(By.name("Itens[0].BOrgs[2].BorgDescription")).sendKeys("M100 - Centro Série 10");
            Thread.sleep(1000);
            driver.findElement(By.name("Itens[0].BOrgs[2].BorgDescription")).sendKeys(Keys.DOWN);
            driver.findElement(By.name("Itens[0].BOrgs[2].BorgDescription")).sendKeys(Keys.ENTER);
            driver.findElement(By.name("Itens[0].Complemento.Value")).sendKeys("AUTOMAÇÃO - QA");
            driver.findElement(By.name("Itens[0].Attributes[3].valor")).sendKeys("MWM - VANIA FIUZA PAIVA");
            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].Quantidade")).sendKeys("100000");

            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].CentroCusto.Text")).sendKeys("1102");
            Thread.sleep(1000);
            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].CentroCusto.Text")).sendKeys(Keys.DOWN);
            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].CentroCusto.Text")).sendKeys(Keys.ENTER);

            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].ctrazao_codigo.Text")).sendKeys("100000");
            Thread.sleep(1000);
            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].ctrazao_codigo.Text")).sendKeys(Keys.DOWN);
            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].ctrazao_codigo.Text")).sendKeys(Keys.ENTER);

            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].Ordem.Text")).sendKeys("000000500001");
            Thread.sleep(1000);
            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].Ordem.Text")).sendKeys(Keys.DOWN);
            driver.findElement(By.name("Itens[0].CostObject.ListCostObject[0].Ordem.Text")).sendKeys(Keys.ENTER);

            System.out.println("Atributos preenchidos");

            driver.findElement(By.id("btnAvancar")).click();
            Thread.sleep(5000);

            driver.findElement(By.xpath("//button[contains(.,'Finalizar')]")).click();

            Thread.sleep(3000);

            screenshot();

            System.out.println("RFQNumero : " + driver.findElement(By.cssSelector("input[id='Requisicao']")).getAttribute("value"));

            driver.close();
        } catch (Exception e) {
            screenshot();
        }
    }

    private void screenshot() throws IOException {
        Date data = new Date();
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("target/screenshots/" + data.getTime() + ".png"));
    }

    private void trocarFrame() {
        System.out.println("Realiza a troca de frame...");

        Integer cont = 0;
        List<WebElement> elementList = driver.findElements(By.tagName("iframe"));

        System.out.println("pegou iframes");

        try {
            while (elementList.size() == 0 && cont != 30) {
                Thread.sleep(500);
                cont++;
            }
            driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

            System.out.println("frame trocado");

        } catch (Exception e) {
            System.out.println("Não foi possível trocar para o Frame... \n" + e);
        }
    }
}
