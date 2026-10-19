package com.AnastasiyaProject.pages;

import com.AnastasiyaProject.pages.elements.TableElements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class MediaLibraryPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(MediaLibraryPage.class);
    private final TableElements table;

    private static final By addNewMediaButtonLocator = By.xpath("//a[@class='page-title-action aria-button-if-js' and contains(text(), 'Add New Media File')]");

    private static final By uploadFileInputLocator = By.xpath("//input[contains(@id, 'html')]");

    private static final By pictureUploadedLocator = By.xpath("//div[@class='attachments-wrapper']//li[contains(@aria-label, 'vogue_picture')]");

    private static final By deleteButtonLocator = By.xpath("//div[@class='actions']//button[@class='button-link delete-attachment']");

    public MediaLibraryPage() {
        super();
        this.table = new TableElements();
    }

    public TableElements table() {
        return this.table;
    }


    public void clickOnAddNewMediabutton() {
        logger.info("User clicks Add Media button");
        findVisibleElement(addNewMediaButtonLocator).click();
    }

    public void uploadFile(String pathForUploading) {
        logger.info("User uploads file and waiting till it is uploaded");
        findExistElement(uploadFileInputLocator).sendKeys(pathForUploading);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(pictureUploadedLocator));
    }

    public void checkThatPictureIsUploaded() {
        logger.info("Verifying that picture is deleted");
        boolean isDisplayed = findVisibleElement(pictureUploadedLocator).isDisplayed();
        Assert.assertTrue(isDisplayed, "Картинка 'vogue_picture' НЕ загрузилась в медиабиблиотеку!");
    }

    public void deleteUploadedPicture() {
        logger.debug("User deletes an uploaded picture");
        findVisibleElement(pictureUploadedLocator).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonLocator));
        findVisibleElement(deleteButtonLocator).click();

        wait.until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().accept();
    }

    public void checkThatPictureIsDeleted() {
        logger.debug("Verification that picture is NOT displayed after deletion");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(deleteButtonLocator));
        List<WebElement> pictures = getDriver().findElements(pictureUploadedLocator);
        boolean isPicturePresent = !pictures.isEmpty();
        Assert.assertFalse(isPicturePresent, "Картинка не удалилась и всё ещё присутствует в медиабиблиотеке!");
    }
}
