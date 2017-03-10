package suite;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.lang.String;
import javax.swing.*;

import static org.apache.commons.lang3.StringUtils.indexOf;
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
    @Parameters({"summ", "day", "bing"})
    public void parameterTestOne(String summ, String day, String bing) {

        WebElement placeholder = sberDriver.findElement(By.xpath("//input[@placeholder='Сумма']"));
        while (!StringUtils.isEmpty(placeholder.getAttribute("value"))) {
            placeholder.sendKeys("\u0008");
        }
        placeholder.sendKeys(summ);

        WebElement radio = sberDriver.findElement(By.xpath("//input[@name='converterDateSelect']/following-sibling::p[.='Выбрать']/preceding-sibling::span[@class='radio']"));
        radio.click();

        WebElement kalend = sberDriver.findElement(By.xpath("//div[@class='filter-block']/div[@class='filter-datepicker input']/span[@class='filter-datepicker-trigger']"));
        kalend.click();

        WebElement day5 = sberDriver.findElement(By.xpath(String.format("//a[contains (@class, 'ui-state-default') and .='%s']", day)));
        day5.click();

        WebElement day5ok = sberDriver.findElement(By.xpath("//span[.='Выбрать']"));
        day5ok.click();

        WebElement button = sberDriver.findElement(By.xpath("//button[.='Показать']"));
        button.click();

        WebElement bingo = new WebDriverWait(sberDriver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='converter-result']/h4/span")));
        Assert.assertTrue(bingo.getText().equalsIgnoreCase(bing));
        System.out.println("bingo = " + bingo.getText());
    }

    @Test
    @Parameters({ "date", "buy", "sell"})
    public void parameterTestTwo(String date, String buy, String sell) {

        WebElement extTab = sberDriver.findElement(By.xpath("//span[.='Расширенная таблица курсов']"));
        extTab.click();

        new WebDriverWait(sberDriver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rates-details-table']")));

        WebElement kalend = new WebDriverWait(sberDriver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='filter-datepicker-detailed']")));
        kalend.clear();
        kalend.sendKeys(date);
        extTab.click();

        new WebDriverWait(sberDriver, 5).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='ui-datepicker-div']"))));

        WebElement button = new WebDriverWait(sberDriver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='Показать']")));
        button.click();

        WebElement buyVal = new WebDriverWait(sberDriver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='details-table']/tbody/tr/td[4]/span")));
        Assert.assertTrue(((String) buyVal.getText().subSequence(0, indexOf(buyVal.getText(), " "))).equalsIgnoreCase(buy));

        buyVal = new WebDriverWait(sberDriver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='details-table']/tbody/tr/td[5]/span")));
        Assert.assertTrue(((String) buyVal.getText().subSequence(0, indexOf(buyVal.getText(), " "))).equalsIgnoreCase(sell));

    }

    @Test
    @Parameters({ "city"})
    public void parameterTestTree(String city) {

        WebElement oneTab = sberDriver.findElement(By.xpath("//span[.='Динамика изменения курсов']"));
        oneTab.click();

        new WebDriverWait(sberDriver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rates-details-graphs']")));

        WebElement cityEl = sberDriver.findElement(By.xpath("//span[@class='region-list__name']"));
        if (cityEl.getText() != city) {
            sberDriver.findElement(By.xpath("//span[@class='region-list__name']")).click();
            cityEl = new WebDriverWait(sberDriver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//a[contains(@class, 'region-list__link') and .='%s']", city))));
            cityEl.click();
        }



        //TODO для проверки всплывающих подсказок
        //Actions actions = new Actions(sberDriver);
        //actions.moveToElement(cityEl).build().perform();

    }



    //завершение
    @AfterSuite
    public void stopBrowser() {

        JOptionPane.showMessageDialog(null, "Всё ОК ??"); //пауза!

        MyWebDriver.quitBrowser();
    }

    // TODO..

}
