package config;

import org.aeonbits.owner.Config;

@Config.Sources("file:src/test/resources/config.properties")
public interface WebConfig extends Config {

  @Key("page.load.strategy")
  String pageLoadStrategy();

  @Key("base.url")
  String baseUrl();

  @Key("browser.size")
  String browserSize();
}
