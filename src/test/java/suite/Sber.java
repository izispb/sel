package suite;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;


public class Sber {

    WebDriver sberDriver;

    //старт
    @BeforeSuite
    @Parameters({"browser"})
    public void beforeTest(String browser) {
        sberDriver = MyWebDriver.getDriver(browser);
        sberDriver.get("http://www.sberbank.ru/ru/quotes/converter");
        System.out.println(sberDriver.getTitle());
        assertEquals(sberDriver.getTitle(), "«Сбербанк» - Калькулятор иностранных валют");
    }

    //конвертация
    @Test
    @Parameters({"summ", "bing"})
    public void test1(String summ, String bing) {

        WebElement placeholder = sberDriver.findElement(By.xpath("//input[@placeholder='Сумма']"));
        while (!StringUtils.isEmpty(placeholder.getAttribute("value"))) {
            placeholder.sendKeys("\u0008");
        }
        placeholder.sendKeys(summ);

        WebElement radio = sberDriver.findElement(By.xpath("//input[@name='converterDateSelect']/following-sibling::p[.='Выбрать']/preceding-sibling::span[@class='radio']"));
        radio.click();

        WebElement kalend = sberDriver.findElement(By.xpath("//div[@class='filter-block']/div[@class='filter-datepicker input']/span[@class='filter-datepicker-trigger']"));
        kalend.click();

        WebElement number6 = sberDriver.findElement(By.xpath("//a[contains (@class, 'ui-state-default ui-state-highlight ui-state-active') and .='6']"));
        number6.click();

        WebElement number6ok = sberDriver.findElement(By.xpath("//span[.='Выбрать']"));
        number6ok.click();

        WebElement button = sberDriver.findElement(By.xpath("//button[.='Показать']"));
        button.click();

        WebElement bingo = sberDriver.findElement(By.xpath("//div[@class='converter-result']/h4/span"));
        Assert.assertTrue(bingo.getText().equalsIgnoreCase(bing));
        System.out.println("bingo = " + bingo.getText() );

    }

    //завершение
    @AfterSuite
    public void stopBrowser() {
        sberDriver.quit();
    }

    // TODO..

}
