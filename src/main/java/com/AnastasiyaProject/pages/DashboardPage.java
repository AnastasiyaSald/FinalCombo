package com.AnastasiyaProject.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(DashboardPage.class);
    private final LeftMenuItemElements leftMenu;

    private static final By refreshItemLocator = By.xpath("//li[@id='wp-admin-bar-updates']//span[@class='ab-icon']");


    public DashboardPage() {
        super();
        this.leftMenu = new LeftMenuItemElements();
    }

    public LeftMenuItemElements leftMenu() {
        return this.leftMenu;
    }

    public String dashboardPageIsOpened() {
        return leftMenu.clickDashboardAndGetUrl();
    }

    public boolean usersLeftMenuItemIsDisplayed() {
        return leftMenu.usersLeftMenuItemIsDisplayed();
    }

    public boolean mediaLeftMenuItemisDisplayed() {
        return leftMenu.mediaLeftMenuItemisDisplayed();
    }

    public boolean postsLeftMenuItemIsDisplayed() {
        return leftMenu.postsLeftMenuItemIsDisplayed();
    }

    public boolean pagesLeftMenuItemIsDisplayed() {
        return leftMenu.pagesItemLeftMenuIsDisplayed();
    }

    public void clickOnPagesItem() {
        leftMenu.clickOnPagesItem();
    }

    public boolean refreshItemIsDisplayed() {
        try {
            return findVisibleElement(refreshItemLocator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public void clickOnMediaItem() {
        leftMenu.clickOnMediaItem();
    }

}
