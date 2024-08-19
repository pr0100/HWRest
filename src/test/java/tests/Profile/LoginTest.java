package tests.Profile;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.BooksSpec.addBookRequestSpec;
import static specs.BooksSpec.booksResponseSpec;
import static tests.TestData.defaultLogin;
import static tests.TestData.elemArrayBooks;

import io.restassured.response.Response;
import com.codeborne.selenide.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.IsbnModel;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.Cookie;
import models.AddBookRequestModel;
import models.AddBookResponseModel;
import org.junit.jupiter.api.Test;
import tests.API.ApiConfig;
import tests.API.AuthorizationApi;

public class LoginTest extends ApiConfig {

  AuthorizationApi authorizationApi = new AuthorizationApi();

  @Test
  void successfulLoginApi() {
    String authCookieTokenKey = "token";
    String authCookieExpiresKey = "expires";
    String authCookieUserIDKey = "userID";

    Response responseUserID = authorizationApi.getLoginResponse();
    String authCookieUserIDValue = responseUserID.path("userId");

    Response responseToken = authorizationApi.getTokenResponse();
    String authCookieTokenValue = responseToken.path("token");
    String authCookieExpiresValue = responseToken.path("expires");

    step("Open an authorized page using cookies", () -> {
      Configuration.pageLoadStrategy = "eager";
      open("https://demoqa.com/profile");
      Cookie authCookieToken = new Cookie(authCookieTokenKey, authCookieTokenValue);
      Cookie authCookieExpires = new Cookie(authCookieExpiresKey, authCookieExpiresValue);
      Cookie authCookieUserID = new Cookie(authCookieUserIDKey, authCookieUserIDValue);

      getWebDriver().manage().addCookie(authCookieToken);
      getWebDriver().manage().addCookie(authCookieExpires);
      getWebDriver().manage().addCookie(authCookieUserID);
      getWebDriver().navigate().refresh();
    });

    step("Check result", () -> {
      $("#userName-value").shouldHave(text(defaultLogin));
    });
  }

  @Test
  void addBooks() {
    AddBookRequestModel regParams = new AddBookRequestModel();
    Response responseUser = authorizationApi.getLoginResponse();
    String authCookieUserIDValue = responseUser.path("userId");

    Response responseToken = authorizationApi.getTokenResponse();
    String authCookieTokenValue = responseToken.path("token");

    step("Get random available Isbn", () -> {
      Response responseAllIsbn = get("BookStore/v1/Books");
      List<IsbnModel> isbnList = new ArrayList<>();
      IsbnModel isbn = new IsbnModel();
      isbn.setIsbn(responseAllIsbn.path("books[" + elemArrayBooks + "].isbn"));
      isbnList.add(isbn);
      regParams.setUserId(authCookieUserIDValue);
      regParams.setCollectionOfIsbns(isbnList);
    });

    AddBookResponseModel response = step("Make request" , () ->
        given(addBookRequestSpec)
            .auth().basic(responseUser.path("username"), responseUser.path("password"))
            .auth().oauth2(authCookieTokenValue)
            .body(regParams)
        .when()
            .post()
        .then()
            .spec(booksResponseSpec)
            .extract().as(AddBookResponseModel.class));

    step("Check result", () ->
      assertEquals(regParams.getCollectionOfIsbns(), response.getBooks()));
  }
}
