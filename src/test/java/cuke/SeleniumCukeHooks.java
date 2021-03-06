package cuke;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static selenium.WebDriverFactory.createDriverFor;

public class SeleniumCukeHooks {
    private static WebDriver driver;

    public static WebDriver driver() {
        return driver;
    }

    @Before("@Selenium")
    public void openSeleniumBrowserWindow() {
        String targetBrowser = System.getProperty("target_browser");

        if (notDefined(targetBrowser)) defaultTo("firefox");
        else setDriverTo(targetBrowser);

        setTimeoutTo(30, SECONDS);
    }

    @After("@Selenium")
    public void closeBrowserWindow() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    private boolean notDefined(String targetBrowser) {
        return targetBrowser == null || isBadAntValue(targetBrowser);
    }

    private void setTimeoutTo(final int timeout, final TimeUnit timeUnit) {
        driver.manage().timeouts().implicitlyWait(timeout, timeUnit);
    }

    private void defaultTo(String targetBrowser) {
        driver = createDriverFor(targetBrowser);
    }

    private void setDriverTo(String targetBrowser) {
        driver = createDriverFor(targetBrowser);
    }

    private boolean isBadAntValue(String targetBrowser) {
        return targetBrowser.startsWith("${");
    }

}
