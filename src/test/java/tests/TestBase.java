package tests;

import static com.codeborne.selenide.Selenide.open;

import config.WebConfig;
import org.aeonbits.owner.ConfigFactory;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

  private static final WebConfig config = ConfigFactory.create(WebConfig.class);

  @BeforeEach
  void setUp() {
    Configuration.pageLoadStrategy = config.pageLoadStrategy();
    Configuration.browserSize = config.browserSize();
    open(config.baseUrl());
  }
}
