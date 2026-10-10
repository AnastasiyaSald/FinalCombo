package webdriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

  private static Properties properties = new Properties();
  private static String CONFIG_PATH = "src/main/resources/project.properties";

  static {
    try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
      properties.load(fis);
    } catch (IOException e) {
      throw new RuntimeException("Cannot load config.properties: " + e.getMessage());
    }
  }
  public static String getProperty(String key) {
    String value = System.getProperty(key); // CLI override: -Dbrowser=firefox
    if (value == null) {
      value = properties.getProperty(key);
    }
    if (value == null) {
      throw new RuntimeException("Property not found: " + key);
    }
    return value.trim();
  }

  public static String getProperty(String key, String defaultValue) {
    try {
      return getProperty(key);
    } catch (RuntimeException e) {
      return defaultValue;
    }
  }
}
