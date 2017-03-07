package suite;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MyWebDriver {

    private static final long IMPLICIT_WAIT_TIMEOUT = 5;
    private static WebDriver webDriver;

    public static WebDriver getDriver(String name) {
        if (webDriver != null) {
            return webDriver;
        } else {
                switch (name) {
                    case "chrome":{
                        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
                        webDriver = new ChromeDriver();
                        break;
                    }
                    case "firefox":{
                        webDriver = new FirefoxDriver();
                        break;
                    }
                    case "ie":{
                        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                        capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                        System.setProperty("webdriver.ie.driver", "C:\\Program Files (x86)\\Internet Explorer\\IEDriverServer.exe");
                        webDriver = new InternetExplorerDriver(capabilities);
                        break;
                    }
                }
            webDriver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
            return webDriver;
        }

        }



    /**
     * Finishes browser
     */
    public static void quitBrowser() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }



}
