package webdriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.latest.log.Log;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;

//singletone
public class Browser {
  private static final Logger logger = LogManager.getLogger(Browser.class);

  private static final long WAIT_TIMEOUT_IN_SECONDS = Long.parseLong(ConfigReader.getProperty("explicit.wait"));
  private static final long PAGE_LOAD_TIMEOUT_IN_SECONDS = Long.parseLong(ConfigReader.getProperty("implicit.wait"));

  private static WebDriver driver;

  private Browser(WebDriver driver) {

  }


  public static WebDriver getDriver() {
    if (driver == null) {
      initDriver();
    }
    return driver;
  }

  private static void initDriver() {
    driver = BrowserFactory.
        createDriver(BrowserType.valueOf(ConfigReader.getProperty("browser", "CHROME")));
    driver.manage().window().maximize();
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT_IN_SECONDS));
    driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT_IN_SECONDS));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIMEOUT_IN_SECONDS));

    logger.info("✅ Browser started");
  }

  public static WebElement findVisibleElement(By locator) {
    logger.debug("looking for visibility of element by locator {}", locator);
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_TIMEOUT_IN_SECONDS));
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public static WebElement findExistElement(By locator) {
    logger.debug("looking for existing element by locator {}", locator);
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_TIMEOUT_IN_SECONDS));
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  public static void close() {
    if (driver != null) {
      driver.quit();
      driver = null;
      logger.info("✅ Browser closed");
    }
  }

  public static JavascriptExecutor getJavascriptExecutor() {
    return (JavascriptExecutor) getDriver();
  }

  public static BrowserType getBrowserType() {
    return BrowserType.valueOf(ConfigReader.getProperty("browser", "CHROME"));
  }

  public static void printBrowserLogs(){
    if(BrowserType.CHROME == getBrowserType()){
      try {
        org.openqa.selenium.logging.LogEntries logs =
            driver.manage().logs().get(
                LogType.BROWSER);

        logger.warn("Browser Console Logs:");
        logs.getAll().forEach(entry ->
            System.out.printf("  [%s] %s: %s%n",
                entry.getLevel(),
                Instant.ofEpochMilli(entry.getTimestamp()),
                entry.getMessage()));
      } catch (Exception e) {
        logger.warn("Log capture not supported: " + e.getMessage());
      }
    }
  }
  public static void consoleLogs() {
    if (BrowserType.CHROME == getBrowserType()) {
      DevTools devTools =
          ((org.openqa.selenium.chrome.ChromeDriver) driver).getDevTools();
      devTools.createSession();

      // Listen to console logs
      devTools.send(Log.enable());
      devTools.addListener(
          Log.entryAdded(),
          entry -> System.out.println(
              "Console [" + entry.getLevel() + "]: " + entry.getText())
      );

      // Trigger some console logs
      ((JavascriptExecutor) driver).executeScript(
          "console.log('Test log message');" +
              "console.warn('Test warning');" +
              "console.error('Test error');");

      logger.info("Console log monitoring demo completed");
    }
  }

}
