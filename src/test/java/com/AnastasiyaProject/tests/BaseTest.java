package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.LoginPage;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.AnastasiyaProject.webdriver.BrowserMultiThread;
import com.AnastasiyaProject.webdriver.ConfigReader;

public class BaseTest {
  protected static final Logger logger = LogManager.getLogger(BaseTest.class);
  @BeforeClass
  public void setUp() {
    logger.info("=== Старт инициализации и открытие браузера ===");
    BrowserMultiThread.getDriver();
    LoginPage loginPage = new LoginPage();
    loginPage.open();
    loginPage.doLogin(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));

    DashboardPage dashboardPage = new DashboardPage();
    Assert.assertTrue(dashboardPage.leftMenu().pagesItemLeftMenuIsDisplayed(), "Главное меню 'Pages' не отображается после авторизации!");
    dashboardPage.leftMenu().clickOnPagesItem();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    logger.info("=== Закрытие браузера ===");
    BrowserMultiThread.close();
  }
  @Attachment(value = "Скриншот при падении в setUp", type = "image/png")
  public byte[] captureScreenshot(WebDriver driver) {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }
}
