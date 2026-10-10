package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.AnastasiyaProject.pages.PagesPage;


public class PageTest extends BaseTest {

    @Test(groups = "pageCreation", dependsOnGroups = "loginRoles")
        public void testCreateNewWordPressPageInDraft() {

            PagesPage pagesPage = new PagesPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.leftMenu().clickOnPagesItem();
            pagesPage.clickOnAddNewPageButton();
            Assert.assertTrue(pagesPage.chooseAPatternModalIsOpened(),"Модальное окно выбора шаблона не открылось!");
            pagesPage.clickOnSelectedPattern();


            String pageTitle = "Nastya Title Page";
            pagesPage.enterTextInTheTitleField(pageTitle);

            String articleText = "Это текст тестовой статьи, созданной автоматическим скриптом.";
            pagesPage.enterTextInTheArticleField(articleText);

            pagesPage.clickSaveDraftButton();
            pagesPage.returnBackToPagesPage();
            pagesPage.table().titleIsDisplayedInTheTable(pageTitle);
        }

        @Test(dependsOnMethods = "testCreateNewWordPressPageInDraft")
        public void publishDraftPage () {
            PagesPage pagesPage = new PagesPage();
            String pageTitle = "Nastya Title Page";
            pagesPage.table().clickOnTheDraftPageItemInTheTable(pageTitle);
            pagesPage.clickPublishButtonTwice();
            pagesPage.returnBackToPagesPage();
        }

        @Test(dependsOnMethods = "publishDraftPage")
    public void makePublishedPageToBeDraft () throws InterruptedException {
            PagesPage pagesPage = new PagesPage();
            String pageTitle = "Nastya Title Page";
            pagesPage.table().clickCheckboxNearCreatedPage(pageTitle);
            pagesPage.table().selectEditFromDropdown();
            pagesPage.table().clickOnApplyButton();
            pagesPage.table().selectDraftOptionInTheDropdown();
            pagesPage.table().clickUpdateButton();
            pagesPage.table().verifyPageStatusIsDraft(pageTitle);
        }

}

