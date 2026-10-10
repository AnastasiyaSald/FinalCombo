package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.PagesPage;
import org.testng.Assert;
import org.testng.annotations.Test;


    public class PageCanBeDeletedTest extends BaseTest {

        String pageTitle = "Nastya Title Page";

        @Test(groups = "deletePage", dependsOnGroups = "pageCreation")
        public void testCreateNewWordPressPageInDraft() {
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.leftMenu().clickOnPagesItem();
            PagesPage pagesPage = new PagesPage();
            pagesPage.clickOnAddNewPageButton();
            Assert.assertTrue(pagesPage.chooseAPatternModalIsOpened(), "Модальное окно выбора шаблона не открылось!");
            pagesPage.clickOnSelectedPattern();

            pagesPage.enterTextInTheTitleField(pageTitle);

            String articleText = "Это текст тестовой статьи, созданной автоматическим скриптом.";
            pagesPage.enterTextInTheArticleField(articleText);

            pagesPage.clickSaveDraftButton();
            pagesPage.returnBackToPagesPage();
            pagesPage.table().titleIsDisplayedInTheTable(pageTitle);
        }

        @Test(dependsOnMethods = "testCreateNewWordPressPageInDraft")
        public void publishDraftPage() {
            PagesPage pagesPage = new PagesPage();
            pagesPage.table().clickOnTheDraftPageItemInTheTable(pageTitle);
            pagesPage.clickPublishButtonTwice();
            pagesPage.returnBackToPagesPage();
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
        public void deleteThePagemethod2() {
            PagesPage pagesPage = new PagesPage();
            String pageTitle = "Nastya Title Page";
            pagesPage.table().hoverOverPageTitleAndClickOnTrashOption(pageTitle);
            pagesPage.clickBrowserBack();
            Assert.assertTrue(pagesPage.pagesPageIsDisplayed());

        }
    }