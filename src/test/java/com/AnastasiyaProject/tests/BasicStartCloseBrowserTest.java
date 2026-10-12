package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.webdriver.BrowserMultiThread;

public class BasicStartCloseBrowserTest {

  public void startBrowser() {
    BrowserMultiThread.getDriver();
  }

  public void stopBrowser() {
    BrowserMultiThread.close();
  }
}
