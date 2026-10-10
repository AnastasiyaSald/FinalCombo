package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.MediaLibraryPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class MediaUploadTest extends BaseTest {

    @Test(groups = "mediaUpload", dependsOnGroups = "deletePage")
    public void testNavigateToMediaLibrary() {
        DashboardPage dashboardPage = new DashboardPage();
        Assert.assertTrue(dashboardPage.leftMenu().mediaLeftMenuItemisDisplayed(),
                "Пункт меню 'Media' не отображается для текущей роли!");
        dashboardPage.leftMenu().clickOnMediaItem();
    }

    @Test(dependsOnMethods = "testNavigateToMediaLibrary", description = "Загрузка изображения в WordPress")
    public void testUploadPicture() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.leftMenu().clickOnMediaItem();
        MediaLibraryPage mediaLibraryPage = new MediaLibraryPage();
        mediaLibraryPage.clickOnAddNewMediabutton();
        mediaLibraryPage.uploadFile("C:\\Users\\A.Saldatsenka\\Documents\\Lightshot\\vogue_picture.jpg");
        mediaLibraryPage.checkThatPictureIsUploaded();
    }

    @Test(dependsOnMethods = "testUploadPicture", description = "Удаление загруженного изображения и проверка удаления")
    public void testDeleteUploadedPicture() {
        MediaLibraryPage mediaLibraryPage = new MediaLibraryPage();
        mediaLibraryPage.deleteUploadedPicture();
        mediaLibraryPage.checkThatPictureIsDeleted();
    }
}