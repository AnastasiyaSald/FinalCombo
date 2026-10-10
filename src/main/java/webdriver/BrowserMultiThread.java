package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//singletone
public class BrowserMultiThread {

  private static final long WAIT_TIMEOUT_IN_SECONDS = Long.parseLong(ConfigReader.getProperty("explicit.wait"));
  private static final long PAGE_LOAD_TIMEOUT_IN_SECONDS = Long.parseLong(ConfigReader.getProperty("implicit.wait"));

  private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

  private BrowserMultiThread(WebDriver driver) {

  }


  public static WebDriver getDriver() {
    if (driver.get() == null) {
      initDriver();
    }
    return driver.get();
  }

  private static void initDriver() {
    driver.set(BrowserFactory.
        createDriver(BrowserType.valueOf(ConfigReader.getProperty("browser", "CHROME"))));
    driver.get().manage().window().maximize();
    driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT_IN_SECONDS));
    driver.get().manage().timeouts().scriptTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT_IN_SECONDS));
    driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIMEOUT_IN_SECONDS));

    System.out.println("✅ Browser started");
  }

  public static WebElement findVisibleElement(By locator) {
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_TIMEOUT_IN_SECONDS));
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public static WebElement findExistElement(By locator) {
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_TIMEOUT_IN_SECONDS));
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  public static void close() {
    if (driver.get() != null) {
      driver.get().quit();
      driver.set(null);
      System.out.println("✅ Browser closed");
    }
  }

  public static JavascriptExecutor getJavascriptExecutor() {
    return (JavascriptExecutor) getDriver();
  }

}
