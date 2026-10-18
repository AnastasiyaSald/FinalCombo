package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.PagesPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Управление страницами WordPress")
@Feature("Удаление страниц")
@Owner("Анастасия")
public class PageCanBeDeletedTest extends BaseTest {

    String pageTitle = "Nastya Title Page";

    @Test(groups = "deletePage")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Создание предусловия: страницы в режиме черновика")
    @Description("Тест создает новую страницу с шаблоном, заполняет заголовок и контент, после чего проверяет её наличие в таблице черновиков.")
    public void testCreateNewWordPressPageInDraft() {
        DashboardPage dashboardPage = new DashboardPage();
        PagesPage pagesPage = new PagesPage();

        Allure.step("Перейти в раздел 'Pages' и нажать кнопку 'Add New Page'", () -> {
            dashboardPage.leftMenu().clickOnPagesItem();
            pagesPage.clickOnAddNewPageButton();
        });

        Allure.step("Выбрать шаблон (паттерн) в модальном окне", () -> {
            Assert.assertTrue(pagesPage.chooseAPatternModalIsOpened(), "Модальное окно выбора шаблона не открылось!");
            pagesPage.clickOnSelectedPattern();
        });

        Allure.step("Заполнить текстовые поля страницы", () -> {
            pagesPage.enterTextInTheTitleField(pageTitle);
            String articleText = "Это текст тестовой статьи, созданной автоматическим скриптом.";
            pagesPage.enterTextInTheArticleField(articleText);
        });

        Allure.step("Сохранить черновик и вернуться к таблице страниц", () -> {
            pagesPage.clickSaveDraftButton();
            pagesPage.returnBackToPagesPage();
        });

        Allure.step("Проверить, что созданный черновик '" + pageTitle + "' успешно отображается в таблице", () -> {
            pagesPage.table().titleIsDisplayedInTheTable(pageTitle);
        });
    }

    @Test(dependsOnMethods = "testCreateNewWordPressPageInDraft")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание предусловия: публикация созданного черновика")
    @Description("Тест берет созданный ранее черновик страницы и выполняет его полную публикацию.")
    public void publishDraftPage() {
        PagesPage pagesPage = new PagesPage();

        Allure.step("Открыть созданный черновик для редактирования", () -> {
            pagesPage.table().clickOnTheDraftPageItemInTheTable(pageTitle);
        });

        Allure.step("Нажать кнопку публикации дважды и вернуться к списку страниц", () -> {
            pagesPage.clickPublishButtonTwice();
            pagesPage.returnBackToPagesPage();
        });
    }

//        @Test(dependsOnMethods = "publishDraftPage")
//        public void deleteThePage() {
//            PagesPage pagesPage = new PagesPage();
//            pagesPage.table().clickCheckboxNearCreatedPage(pageTitle);
//            pagesPage.table().selectMoveToTrashtFromDropdown();
//            pagesPage.clickOnApplyButton();
//            Assert.assertTrue(pagesPage.table().checkThatPageIsDeleted(pageTitle));
//        }

    @Test(dependsOnMethods = "publishDraftPage")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Удаление опубликованной страницы через Trash")
    @Description("Тест наводит курсор на заголовок страницы, нажимает на появившуюся опцию 'Trash', возвращается назад и проверяет статус.")
    public void deleteThePagemethod2() {
        PagesPage pagesPage = new PagesPage();
        String pageTitle = "Nastya Title Page";

        Allure.step("Навести курсор на заголовок страницы '" + pageTitle + "' и переместить её в корзину (Trash)", () -> {
            pagesPage.table().hoverOverPageTitleAndClickOnTrashOption(pageTitle);
        });

        Allure.step("Вернуться назад в браузере", () -> {
            pagesPage.clickBrowserBack();
        });

        Allure.step("Проверить, что страница со списком записей успешно отображается", () -> {
            Assert.assertTrue(pagesPage.pagesPageIsDisplayed(), "Страница со списком Pages не отображается!");
        });
    }
}