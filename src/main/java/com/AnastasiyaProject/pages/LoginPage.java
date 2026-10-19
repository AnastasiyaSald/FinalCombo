package com.AnastasiyaProject.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    private static final By userNameLocator = By.id("user_login");
    private static final By passwordLocator = By.id("user_pass");
    private static final By loginButtonLocator = By.id("wp-submit");

    public LoginPage() {
        super();
    }

    public void open() {
        getDriver().get(baseUrl);
    }

    public void doLogin (String userName, String password) {
        logger.info("User enters name, password and click Login button");
        enterUsername (userName);
        enterPassword (password);
        clickLoginButton();
    }

    public void enterUsername (String userName) {
        findVisibleElement(userNameLocator).sendKeys(userName);
    }

    public void enterPassword (String password) {
        findVisibleElement(passwordLocator).sendKeys(password);
    }

    public void clickLoginButton() {
        findVisibleElement(loginButtonLocator).click();
    }

}
