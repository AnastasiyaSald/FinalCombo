package webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class BrowserFactory {
  private static final boolean HEADLESS = ConfigReader.getProperty("HEADLESS","false").equals("true");

  public static WebDriver createDriver(BrowserType browserType){
    WebDriver driver = null;
    switch (browserType){
      case CHROME ->{
        // Automatically downloads the correct ChromeDriver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        if(HEADLESS == true) {
          options.addArguments("--headless");  // uncomment to run without UI
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // Common Chrome options for stability
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        // Disable password manager popup
        options.addArguments("--disable-save-password-bubble");

        driver = new ChromeDriver(options);

      }
      case FIREFOX ->{
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);
      }


    }
    return driver;
  }
}
