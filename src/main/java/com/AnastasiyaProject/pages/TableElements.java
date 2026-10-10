package com.AnastasiyaProject.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class TableElements extends BasePage {
    private static final Logger logger = LogManager.getLogger(TableElements.class);
    private static final By bulkActionsDropdownLocator = By.xpath("//div[@class='tablenav top']//select[@id='bulk-action-selector-top']");
    private static final By applyButtonLocator = By.xpath("//input[@class='button action' and contains(@id, 'doaction')]");
    private static final By statusDropdownLocator = By.xpath("//div[@class='inline-edit-col']//div[@class='inline-edit-group wp-clearfix']//select[@name='_status' and .//option[contains(text(), '— No Change —')]]");
    private static final By updateButtonLocator = By.xpath("//div[@class='submit inline-edit-save']//input[@id='bulk_edit']");

    public TableElements() {
        super();
    }

    public By getBulkActionsDropdownLocator() {
        return bulkActionsDropdownLocator;
    }

    public By getApplyButtonLocator() {
        return applyButtonLocator;
    }

    public By getStatusDropdownLocator() {
        return statusDropdownLocator;
    }

    public By getUpdateButtonLocator() {
        return updateButtonLocator;
    }


    private By getTitleInTableLocator(String expectedTitle) {
        String xpathExpression = String.format(
                "//table[@class='wp-list-table widefat fixed striped table-view-list pages']//tbody[@id='the-list']//a[@class='row-title' and contains(text(), '%s')]",
                expectedTitle
        );
        return By.xpath(xpathExpression);
    }

    private By getCheckboxNearCreatedPageLocator(String expectedTitle) {
        String xpathExpression = String.format(
                "//tr[.//span[@class='screen-reader-text' and contains(text(), '%s')]]//input[@type='checkbox']",
                expectedTitle);
        return By.xpath(xpathExpression);
    }

    private By updatedTitleInTableWithDraftWordLocator(String expectedTitle) {
        String xpathExpression = String.format(
                "//tr[.//a[@class='row-title' and contains(text(), '%s')] and .//span[@class='post-state' and contains(text(), 'Draft')]]",
                expectedTitle
        );
        return By.xpath(xpathExpression);
    }


    public void clickOnTheDraftPageItemInTheTable(String expectedTitle) {
        By dynamicLocator = getTitleInTableLocator(expectedTitle);
        WebElement titleClickableRow = findVisibleElement(dynamicLocator);
        titleClickableRow.click();
    }

    public void clickCheckboxNearCreatedPage(String expectedTitle) {
        By dynamicCheckBoxLocator = getCheckboxNearCreatedPageLocator(expectedTitle);
        WebElement checkboxIsFound = findVisibleElement(dynamicCheckBoxLocator);
        checkboxIsFound.click();
    }

    public void selectEditFromDropdown() {
        logger.info("User selects Edit option in the dropdown");
        WebElement dropdownElement = findVisibleElement(bulkActionsDropdownLocator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Edit");
    }

    public void clickOnApplyButton() {
        findVisibleElement(applyButtonLocator).click();
    }

    public void selectDraftOptionInTheDropdown() {
        logger.info("User selects draft option in the dropdown");
        WebElement dropdownElement = findVisibleElement(statusDropdownLocator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Draft");
    }

    public void clickUpdateButton() {
        findVisibleElement(updateButtonLocator).click();
    }

    public void verifyPageStatusIsDraft(String expectedTitle) {
        logger.info("Verification that page is in draft state");
        By draftPageLocator = updatedTitleInTableWithDraftWordLocator(expectedTitle);
        boolean isDraftDisplayed = findVisibleElement(draftPageLocator).isDisplayed();
        Assert.assertTrue(isDraftDisplayed, "Страница '" + expectedTitle + "' со статусом 'Draft' не найдена в таблице!");
    }

    public void titleIsDisplayedInTheTable(String expectedTitle) {
        logger.info("Verification that title is displayed in the table");
        By dynamicLocator = getTitleInTableLocator(expectedTitle);
        WebElement titleRow = findVisibleElement(dynamicLocator);
        Assert.assertTrue(titleRow.isDisplayed(), "Созданная страница с заголовком '" + expectedTitle + "' не найдена в таблице!");
    }

    public void hoverOverPageTitleAndClickOnTrashOption(String expectedTitle) {
        logger.info("Hovering over page title '{}' and clicking 'Trash' button", expectedTitle);
        By titleLocator = getTitleInTableLocator(expectedTitle);
        WebElement pageTitle = findVisibleElement(titleLocator);
        Actions actions = new Actions(getDriver());
        actions.moveToElement(pageTitle).perform();
        By trashButtonLocator = getTrashButtonInRowLocator(expectedTitle);
        WebDriverWait localWait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement trashButton = localWait.until(ExpectedConditions.elementToBeClickable(trashButtonLocator));
        trashButton.click();
    }

    private By getTrashButtonInRowLocator(String expectedTitle) {
        String xpathExpression = String.format(
                "//tr[.//a[@class='row-title' and contains(text(), '%s')]]//span[@class='trash']/a",
                expectedTitle
        );
        return By.xpath(xpathExpression);
    }

    public void selectMoveToTrashtFromDropdown() {
        logger.debug("Correct option *move to trash* is selected in the dropdown");
        WebElement dropdownElement = findVisibleElement(bulkActionsDropdownLocator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(2);

    }

    public boolean checkThatPageIsDeleted(String expectedTitle) {
        By titleLocator = getTitleInTableLocator(expectedTitle);
        try {
            org.openqa.selenium.support.ui.WebDriverWait quickWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(5));
            return quickWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(titleLocator));
        } catch (Exception e) {
            return false;
        }
    }

}

