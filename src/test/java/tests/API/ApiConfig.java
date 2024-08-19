package tests.API;

import static io.restassured.RestAssured.baseURI;

import org.junit.jupiter.api.BeforeAll;

public class ApiConfig {

  @BeforeAll
  public static void setUp()
  {
    baseURI = "https://demoqa.com/";
  }

}
