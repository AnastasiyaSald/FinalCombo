package com.AnastasiyaProject.pages;

import com.AnastasiyaProject.pages.elements.LeftMenuItemElements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

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

    public boolean refreshItemIsDisplayed() {
        try {
            return findVisibleElement(refreshItemLocator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

//    public void clickOnMediaItem() {
//        leftMenu.clickOnMediaItem();
//    }

}
