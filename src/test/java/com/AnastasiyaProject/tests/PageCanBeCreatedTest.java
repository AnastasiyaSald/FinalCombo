package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.PagesPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PageCanBeCreatedTest extends BaseTest {

    private static final String EXPECTED_TITLE = "Nastya Title";

    @Test(dependsOnGroups = "mediaUpload")
    public void pageIsCreated() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.leftMenu().clickOnPagesItem();
        PagesPage pagesPage = new PagesPage();
        pagesPage.clickOnAddNewPageButton();
        Assert.assertTrue(pagesPage.chooseAPatternModalIsOpened(), "Модальное окно выбора шаблона не открылось!");
        pagesPage.clickOnSelectedPattern();

        pagesPage.enterTextInTheTitleField(EXPECTED_TITLE);

        String articleText = "Audrey Hepburn is a legendary British actress who became a symbol of refined beauty, fashion, and the Golden Age of Hollywood thanks to her iconic roles in the films *Roman Holiday* and *Breakfast at Tiffany's*.";
        pagesPage.enterTextInTheArticleField(articleText);
        pagesPage.clickPublishButtonTwice();
        pagesPage.returnBackToPagesPage();
        pagesPage.table().titleIsDisplayedInTheTable(EXPECTED_TITLE);
    }
}