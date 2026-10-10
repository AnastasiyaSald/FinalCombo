package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import webdriver.BrowserMultiThread;
import webdriver.ConfigReader;

public class BaseTest {

  @BeforeClass
  public void setUp() {
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
    BrowserMultiThread.close();
  }
}
