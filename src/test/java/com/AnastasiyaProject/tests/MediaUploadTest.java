package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.pages.DashboardPage;
import com.AnastasiyaProject.pages.MediaLibraryPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.nio.file.Paths;


@Epic("Управление медиафайлами WordPress")
@Feature("Загрузка и удаление изображения в библиотеке Media")
@Owner("Анастасия")
public class MediaUploadTest extends BaseTest {

    @Test(groups = "mediaUpload")
    @Severity(SeverityLevel.NORMAL)
    @Story("Переход в библиотеку медиафайлов")
    @Description("Тест проверяет доступность раздела 'Media' в боковом меню и осуществляет переход на страницу библиотеки.")
    public void testNavigateToMediaLibrary() {
        DashboardPage dashboardPage = new DashboardPage();

        Allure.step("Проверить отображение пункта меню 'Media'", () -> {
            Assert.assertTrue(dashboardPage.leftMenu().mediaLeftMenuItemisDisplayed(),
                    "Пункт меню 'Media' не отображается для текущей роли!");
        });

        Allure.step("Кликнуть по пункту меню 'Media' для перехода в библиотеку", () -> {
            dashboardPage.leftMenu().clickOnMediaItem();
        });
    }

    @Test(dependsOnMethods = "testNavigateToMediaLibrary", description = "Загрузка изображения в WordPress")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Загрузка изображения")
    @Description("Тест динамически определяет путь к картинке из папки ресурсов проекта и загружает её в WordPress с последующей валидацией.")
    public void testUploadPicture() {
        DashboardPage dashboardPage = new DashboardPage();
        MediaLibraryPage mediaLibraryPage = new MediaLibraryPage();

        Allure.step("Открыть форму добавления нового медиафайла", () -> {
            dashboardPage.leftMenu().clickOnMediaItem();
            mediaLibraryPage.clickOnAddNewMediabutton();
        });

        String absolutePath = Allure.step("Получить абсолютный путь к файлу из ресурсов проекта", () -> {
            URL resource = getClass().getClassLoader().getResource("files/vogue_picture.jpg");
            Assert.assertNotNull(resource, "Файл vogue_picture.jpg не найден в ресурсах проекта!");
            try {
                return Paths.get(resource.toURI()).toFile().getAbsolutePath();
            } catch (Exception e) {
                logger.error("Не удалось получить абсолютный путь к файлу", e);
                Assert.fail("Ошибка при определении пути к файлу: " + e.getMessage());
                return "";
            }
        });

        Allure.step("Загрузить файл на сервер. Путь: " + absolutePath, () -> {
            logger.info("Загружаем изображение из ресурсов проекта: {}", absolutePath);
            mediaLibraryPage.uploadFile(absolutePath);
        });

        Allure.step("Проверить, что изображение появилось в библиотеке", () -> {
            mediaLibraryPage.checkThatPictureIsUploaded();
        });
    }

    @Test(dependsOnMethods = "testUploadPicture", description = "Удаление загруженного изображения и проверка удаления")
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление изображения")
    @Description("Тест выбирает загруженную ранее картинку, удаляет её из библиотеки навсегда и проверяет её отсутствие.")
    public void testDeleteUploadedPicture() {
        MediaLibraryPage mediaLibraryPage = new MediaLibraryPage();

        Allure.step("Удалить загруженное изображение через панель управления", () -> {
            mediaLibraryPage.deleteUploadedPicture();
        });

        Allure.step("Убедиться, что изображение пропало из списка медиафайлов", () -> {
            mediaLibraryPage.checkThatPictureIsDeleted();
        });
    }
}