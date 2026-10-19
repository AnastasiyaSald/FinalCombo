package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.PagesPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Управление страницами WordPress")
@Feature("Создание страниц")
@Owner("Анастасия")
public class PageCanBeCreatedTest extends BaseTest {

    private static final String EXPECTED_TITLE = "Nastya Title";

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Прямое создание и мгновенная публикация новой страницы")
    @Description("Тест проверяет полный цикл создания страницы: переход в раздел, выбор шаблона, заполнение контента про Одри Хепбёрн, двойную публикацию и проверку наличия страницы в общей таблице.")
    public void pageIsCreated() {
        DashboardPage dashboardPage = new DashboardPage();
        PagesPage pagesPage = new PagesPage();

        Allure.step("Перейти в раздел 'Pages' и инициировать создание страницы", () -> {
            dashboardPage.leftMenu().clickOnPagesItem();
            pagesPage.clickOnAddNewPageButton();
        });

        Allure.step("Проверить открытие модального окна шаблонов и выбрать паттерн", () -> {
            Assert.assertTrue(pagesPage.chooseAPatternModalIsOpened(), "Модальное окно выбора шаблона не открылось!");
            pagesPage.clickOnSelectedPattern();
        });

        Allure.step("Заполнить заголовок страницы текстом: '" + EXPECTED_TITLE + "' и ввести текст статьи", () -> {
            pagesPage.enterTextInTheTitleField(EXPECTED_TITLE);
            String articleText = "Audrey Hepburn is a legendary British actress who became a symbol of refined beauty, fashion, and the Golden Age of Hollywood thanks to her iconic roles in the films *Roman Holiday* and *Breakfast at Tiffany's*.";
            pagesPage.enterTextInTheArticleField(articleText);
        });

        Allure.step("Выполнить двойное подтверждение публикации и вернуться к списку страниц", () -> {
            pagesPage.clickPublishButtonTwice();
            pagesPage.returnBackToPagesPage();
        });

        Allure.step("Проверить, что опубликованная страница '" + EXPECTED_TITLE + "' успешно отображается в таблице данных", () -> {
            pagesPage.table().titleIsDisplayedInTheTable(EXPECTED_TITLE);
        });
    }
}