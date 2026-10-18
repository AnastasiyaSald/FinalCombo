package com.AnastasiyaProject.tests;

import com.AnastasiyaProject.webdriver.BrowserMultiThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasicStartCloseBrowserTest {
  protected static final Logger logger = LogManager.getLogger(BasicStartCloseBrowserTest.class);
  public void startBrowser() {
    logger.info("=== Старт инициализации и открытие браузера ===");
    BrowserMultiThread.getDriver();
  }

  public void stopBrowser() {
    logger.info("=== Закрытие браузера ===");
    BrowserMultiThread.close();
  }
}
