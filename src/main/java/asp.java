import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class asp {
    protected static DesiredCapabilities capabilities;
    private static WebDriver driver;
    private static WebDriverWait wait;

    @Test
    public void testComHeadlessBrowserComHtmlUnitDriver() throws InterruptedException, IOException {
        File file = new File("/home/fernando/Downloads/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
        Date data = new Date();
        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
        caps.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);

        driver = new PhantomJSDriver(caps);

        driver.get("https://qa.me.com.br/Default.asp?_OrigemID=1");

        Thread.sleep(3000);

        //wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

        driver.findElement(By.name("LoginName")).clear();
        driver.findElement(By.name("LoginName")).sendKeys("CNK_ANJUNIOR");

        driver.findElement(By.name("RAWSenha")).clear();
        driver.findElement(By.name("RAWSenha")).sendKeys("qualidade@123");

        driver.findElement(By.cssSelector(".botaoLogin")).click();

        Thread.sleep(5000);

        if (driver.getCurrentUrl().contains("Timezone")) {
            driver.switchTo().frame("TimezonePopup");
            driver.findElement(By.id("btnAcceptTimeZone")).click();
        }

        driver.findElement(By.cssSelector("a[href='ListaTodosProdutosIE.asp?processo=6']")).click();
        Thread.sleep(3000);

        Select combo = new Select(driver.findElement(By.name("BOrg_545")));
        combo.selectByIndex(1);
        driver.findElement(By.id("btnOKGravaBOrg")).click();
        driver.findElement(By.id("btnOKGravaBOrg")).click();
        Thread.sleep(1000);


        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("/home/fernando/Imagens/screenshots/" + data + ".png"));


        Thread.sleep(3000);

        Assert.assertEquals("Mercado Eletrônico Definição de Fuso Horário", driver.getTitle());

        driver.close();
    }
}
