package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.AnastasiyaProject.pages.PagesPage;

@Epic("Управление страницами WordPress")
@Feature("Жизненный цикл страницы (Черновики и Публикация)")
@Owner("Анастасия")
public class PageTest extends BaseTest {

    @Test(groups = "pageCreation")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Создание новой страницы в режиме черновика")
    @Description("Тест проверяет процесс создания страницы: выбор шаблона, заполнение заголовка и контента, сохранение в черновики и валидацию в таблице.")
    public void testCreateNewWordPressPageInDraft() {
        PagesPage pagesPage = new PagesPage();
        DashboardPage dashboardPage = new DashboardPage();

        Allure.step("Перейти в раздел 'Pages' и инициировать создание новой страницы", () -> {
            dashboardPage.leftMenu().clickOnPagesItem();
            pagesPage.clickOnAddNewPageButton();
        });

        Allure.step("Выбрать шаблон (паттерн) в модальном окне", () -> {
            Assert.assertTrue(pagesPage.chooseAPatternModalIsOpened(), "Модальное окно выбора шаблона не открылось!");
            pagesPage.clickOnSelectedPattern();
        });

        String pageTitle = "Nastya Title Page";
        String articleText = "Это текст тестовой статьи, созданной автоматическим скриптом.";

        Allure.step("Заполнить заголовок страницы и текст статьи", () -> {
            pagesPage.enterTextInTheTitleField(pageTitle);
            pagesPage.enterTextInTheArticleField(articleText);
        });

        Allure.step("Сохранить черновик и вернуться к списку страниц", () -> {
            pagesPage.clickSaveDraftButton();
            pagesPage.returnBackToPagesPage();
        });

        Allure.step("Проверить, что созданная страница отображается в таблице", () -> {
            pagesPage.table().titleIsDisplayedInTheTable(pageTitle);
        });
    }

    @Test(dependsOnMethods = "testCreateNewWordPressPageInDraft")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Публикация страницы из черновика")
    @Description("Тест берет ранее созданный черновик страницы, переходит в редактор и дважды подтверждает его публикацию.")
    public void publishDraftPage() {
        PagesPage pagesPage = new PagesPage();
        String pageTitle = "Nastya Title Page";

        Allure.step("Открыть созданный черновик '" + pageTitle + "' для редактирования", () -> {
            pagesPage.table().clickOnTheDraftPageItemInTheTable(pageTitle);
        });

        Allure.step("Опубликовать страницу (двойное подтверждение) и вернуться назад", () -> {
            pagesPage.clickPublishButtonTwice();
            pagesPage.returnBackToPagesPage();
        });
    }

    @Test(dependsOnMethods = "publishDraftPage")
    @Severity(SeverityLevel.NORMAL)
    @Story("Перевод опубликованной страницы обратно в черновик")
    @Description("Тест использует массовое редактирование через чекбокс в таблице, чтобы вернуть статус опубликованной страницы обратно в 'Draft'.")
    public void makePublishedPageToBeDraft() throws InterruptedException {
        PagesPage pagesPage = new PagesPage();
        String pageTitle = "Nastya Title Page";

        Allure.step("Выбрать страницу '" + pageTitle + "' через чекбокс и открыть форму быстрого редактирования", () -> {
            pagesPage.table().clickCheckboxNearCreatedPage(pageTitle);
            pagesPage.table().selectEditFromDropdown();
            pagesPage.table().clickOnApplyButton();
        });

        Allure.step("Сменить статус страницы на 'Draft' и сохранить изменения", () -> {
            pagesPage.table().selectDraftOptionInTheDropdown();
            pagesPage.table().clickUpdateButton();
        });

        Allure.step("Проверить, что статус страницы в таблице успешно изменился на 'Draft'", () -> {
            pagesPage.table().verifyPageStatusIsDraft(pageTitle);
        });
    }
}

