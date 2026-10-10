package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = "loginRoles")
public class LoginForDifferentRolesTest extends BasicStartCloseBrowserTest {

    protected final String baseUrl = "https://dev-wordpress-fcdbgyfxfuetftf5.westus2-01.azurewebsites.net/wp-admin";

    @BeforeMethod // Запустится пред КАЖДЫМ тестом роли
    public void setupMethod() {
        startBrowser();
    }

    @AfterMethod(alwaysRun = true) // Перезапустит браузер после КАЖДОЙ роли
    public void tearDownMethod() {
        stopBrowser();
    }

    @Test(priority = 1)
    public void loginAsAdmin() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.doLogin("NastyaSald", "7765292Sold!");

        DashboardPage dashboardPage = new DashboardPage();
        String expectedUrl = baseUrl + "/index.php";
        Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
        Assert.assertTrue(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - users item is NOT present");
        Assert.assertTrue(dashboardPage.refreshItemIsDisplayed(), "User is logged in via incorrect role - refresh item is NOT present");
    }

    @Test(priority = 2)
    public void loginAsContributor() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.doLogin("NastyaContributor", "&2DO0#OY0Klf6Oatt6AMaGHD");

        DashboardPage dashboardPage = new DashboardPage();
        String expectedUrl = baseUrl + "/index.php";
        Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
        Assert.assertFalse(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - users item is displayed");
        Assert.assertFalse(dashboardPage.leftMenu().mediaLeftMenuItemisDisplayed(), "User is logged in via incorrect role - media item is displayed");
        Assert.assertFalse(dashboardPage.leftMenu().pagesItemLeftMenuIsDisplayed(), "User is logged in via incorrect role - pages are present");
        Assert.assertTrue(dashboardPage.leftMenu().postsLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - Posts item is NOT displayed");
    }

    @Test(priority = 3)
    public void loginAsAuthor() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.doLogin("NastyaAuthor", "Et0qMiktu6G2YSN7");

        DashboardPage dashboardPage = new DashboardPage();
        String expectedUrl = baseUrl + "/index.php";
        Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
        Assert.assertFalse(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - users are NOT present");
        Assert.assertTrue(dashboardPage.leftMenu().mediaLeftMenuItemisDisplayed(), "User is logged in via incorrect role- media is NOT present");
        Assert.assertTrue(dashboardPage.leftMenu().postsLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - posts are NOT present");
        Assert.assertFalse(dashboardPage.leftMenu().pagesItemLeftMenuIsDisplayed(), "User is logged in via incorrect role - pages are present");
    }


    @Test(priority = 4)
    public void loginAsSubscriber() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.doLogin("NastyaSubscriber", "@L7z9blnfyWtoMas8PSoY");

        DashboardPage dashboardPage = new DashboardPage();
        String expectedUrl = baseUrl + "/index.php";
        Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
        Assert.assertFalse(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role, this item is for Admin role");
    }

    @Test(priority = 5)
    public void loginAsEditor() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.doLogin("NastyaEditor", "ElT4s@PBi!Pp7xZ9Z");

        DashboardPage dashboardPage = new DashboardPage();
        String expectedUrl = baseUrl + "/index.php";
        Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
        Assert.assertFalse(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - users are NOT present");
        Assert.assertTrue(dashboardPage.leftMenu().mediaLeftMenuItemisDisplayed(), "User is logged in via incorrect role- media is NOT present");
        Assert.assertTrue(dashboardPage.leftMenu().postsLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - posts are NOT present");
        Assert.assertTrue(dashboardPage.leftMenu().pagesItemLeftMenuIsDisplayed(), "User is logged in via incorrect role - pages are NOT present");
    }
}
