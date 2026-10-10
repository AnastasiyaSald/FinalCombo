package com.AnastasiyaProject.tests;

import webdriver.BrowserMultiThread;

public class BasicStartCloseBrowserTest {

  public void startBrowser() {
    BrowserMultiThread.getDriver();
  }

  public void stopBrowser() {
    BrowserMultiThread.close();
  }
}
