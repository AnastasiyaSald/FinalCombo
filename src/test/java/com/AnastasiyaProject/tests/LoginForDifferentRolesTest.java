package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Авторизация в системе")
@Feature("Проверка прав доступа для различных ролей пользователей")
@Owner("Анастасия")
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
    @Severity(SeverityLevel.BLOCKER)
    @Story("Авторизация под ролью Administrator")
    @Description("Проверяем доступность панели управления и наличие административных разделов (Users, Refresh).")
    public void loginAsAdmin() {
        Allure.step("Открыть страницу авторизации и войти как Администратор", () -> {
                    LoginPage loginPage = new LoginPage();
                    loginPage.open();
                    loginPage.doLogin("NastyaSald", "7765292Sold!");
                });

        Allure.step("Проверить отображение элементов интерфейса для Администратора", () -> {
            DashboardPage dashboardPage = new DashboardPage();
            String expectedUrl = baseUrl + "/index.php";

            Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
            Assert.assertTrue(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - users item is NOT present");
            Assert.assertTrue(dashboardPage.refreshItemIsDisplayed(), "User is logged in via incorrect role - refresh item is NOT present");
        });
    }


    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация под ролью Contributor")
    @Description("Проверяем, что Контрибьютор видит раздел Posts, но не имеет доступа к Users, Media и Pages.")
    public void loginAsContributor() {
        Allure.step("Открыть страницу авторизации и войти как Контрибьютор", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.open();
            loginPage.doLogin("NastyaContributor", "&2DO0#OY0Klf6Oatt6AMaGHD");
        });

        Allure.step("Проверить ограничения интерфейса для Контрибьютора", () -> {
            DashboardPage dashboardPage = new DashboardPage();
            String expectedUrl = baseUrl + "/index.php";

            Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
            Assert.assertFalse(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - users item is displayed");
            Assert.assertFalse(dashboardPage.leftMenu().mediaLeftMenuItemisDisplayed(), "User is logged in via incorrect role - media item is displayed");
            Assert.assertFalse(dashboardPage.leftMenu().pagesItemLeftMenuIsDisplayed(), "User is logged in via incorrect role - pages are present");
            Assert.assertTrue(dashboardPage.leftMenu().postsLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - Posts item is NOT displayed");
        });
    }

    @Test(priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация под ролью Author")
    @Description("Проверяем, что Автор имеет доступ к разделам Media и Posts, но скрыты разделы Users и Pages.")
    public void loginAsAuthor() {
        Allure.step("Открыть страницу авторизации и войти как Автор", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.open();
            loginPage.doLogin("NastyaAuthor", "Et0qMiktu6G2YSN7");
        });

        Allure.step("Проверить доступные разделы интерфейса для Автора", () -> {
            DashboardPage dashboardPage = new DashboardPage();
            String expectedUrl = baseUrl + "/index.php";

            Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
            Assert.assertFalse(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - users are NOT present");
            Assert.assertTrue(dashboardPage.leftMenu().mediaLeftMenuItemisDisplayed(), "User is logged in via incorrect role- media is NOT present");
            Assert.assertTrue(dashboardPage.leftMenu().postsLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - posts are NOT present");
            Assert.assertFalse(dashboardPage.leftMenu().pagesItemLeftMenuIsDisplayed(), "User is logged in via incorrect role - pages are present");
        });
    }


    @Test(priority = 4)
    @Severity(SeverityLevel.NORMAL)
    @Story("Авторизация под ролью Subscriber")
    @Description("Проверяем базовый доступ Подписчика и отсутствие административного меню Users.")
    public void loginAsSubscriber() {
        Allure.step("Открыть страницу авторизации и войти как Подписчик", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.open();
            loginPage.doLogin("NastyaSubscriber", "@L7z9blnfyWtoMas8PSoY");
        });

        Allure.step("Проверить интерфейс Подписчика", () -> {
            DashboardPage dashboardPage = new DashboardPage();
            String expectedUrl = baseUrl + "/index.php";

            Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
            Assert.assertFalse(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role, this item is for Admin role");
        });
    }

    @Test(priority = 5)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация под ролью Editor")
    @Description("Проверяем доступность разделов Media, Posts и Pages для Редактора при отсутствии раздела Users.")
    public void loginAsEditor() {
        Allure.step("Открыть страницу авторизации и войти как Редактор", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.open();
            loginPage.doLogin("NastyaEditor", "ElT4s@PBi!Pp7xZ9Z");
        });

        Allure.step("Проверить широкие права доступа для Редактора", () -> {
            DashboardPage dashboardPage = new DashboardPage();
            String expectedUrl = baseUrl + "/index.php";

            Assert.assertEquals(dashboardPage.leftMenu().clickDashboardAndGetUrl(), expectedUrl, "DashboardPage is NOT opened");
            Assert.assertFalse(dashboardPage.leftMenu().usersLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - users are NOT present");
            Assert.assertTrue(dashboardPage.leftMenu().mediaLeftMenuItemisDisplayed(), "User is logged in via incorrect role- media is NOT present");
            Assert.assertTrue(dashboardPage.leftMenu().postsLeftMenuItemIsDisplayed(), "User is logged in via incorrect role - posts are NOT present");
            Assert.assertTrue(dashboardPage.leftMenu().pagesItemLeftMenuIsDisplayed(), "User is logged in via incorrect role - pages are NOT present");
        });
    }
}
