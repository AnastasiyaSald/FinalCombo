package com.AnastasiyaProject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LeftMenuItemElements extends BasePage {


    private static final By pagesItemLeftMenuLocator = By.xpath("//ul[@id='adminmenu']//div[@class='wp-menu-name' and contains(text(), 'Pages')]");
    private static final By dashboardPageLeftMenuItemLocator = By.xpath("//div[@id='adminmenuwrap']//div[@class='wp-menu-name' and contains(text(), 'Dashboard')]");
    private static final By usersLeftMenuItemLocator = By.xpath("//div[@class='wp-menu-name' and contains(text(), 'Users')]");
    private static final By mediaLeftMenuItemLocator = By.xpath("//ul[@id='adminmenu']//div[@class='wp-menu-name' and contains(text(), 'Media')]");
    private static final By postsLeftMenuItemLocator = By.xpath("//div[@id='adminmenuwrap']//div[@class='wp-menu-name' and contains(text(), 'Posts')]");

    public LeftMenuItemElements() {
        super();
    }

    public boolean pagesItemLeftMenuIsDisplayed() {
        try {
            return findVisibleElement(pagesItemLeftMenuLocator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public void clickOnPagesItem() {
        WebDriverWait localWait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        WebElement pagesItem = localWait.until(ExpectedConditions.elementToBeClickable(pagesItemLeftMenuLocator));
        pagesItem.click();
    }

    public String clickDashboardAndGetUrl() {
        findVisibleElement(dashboardPageLeftMenuItemLocator).click();
        return getDriver().getCurrentUrl();
    }

    public boolean usersLeftMenuItemIsDisplayed() {
        try {
            return findVisibleElement(usersLeftMenuItemLocator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean mediaLeftMenuItemisDisplayed() {
        try {
            return findVisibleElement(mediaLeftMenuItemLocator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean postsLeftMenuItemIsDisplayed() {
        try {
            return findVisibleElement(postsLeftMenuItemLocator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public void clickOnMediaItem() {
        WebDriverWait localWait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        WebElement mediaItem = localWait.until(ExpectedConditions.elementToBeClickable(mediaLeftMenuItemLocator));
        mediaItem.click();
    }



}
