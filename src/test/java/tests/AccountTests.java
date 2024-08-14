package tests;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.AccountSpec.registrRequestSpec;
import static specs.AccountSpec.registrResponseSpec;
import static specs.AccountSpec.tokenRequestSpec;
import static specs.AccountSpec.tokenResponseSpec;
import static specs.AccountSpec.unregistrResponseSpec;

import com.github.javafaker.Faker;
import models.ErrorResponseModel;
import models.TokenModel;
import models.UserAccountModel;
import models.UserAccountResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты для методов Account")
public class AccountTests {
  private static final Faker faker = new Faker();
  private final String userName = faker.name().username();
  private final String successPasswd = faker.internet().password(8, 15, true, true, true);
  private final String errorPasswdLength = faker.internet().password(1, 5);
  private final String errorPasswdDigit = faker.internet().password(8, 9, true, true, false);

  @BeforeAll
  public static void setUp()
  {
    baseURI = "https://demoqa.com/Account/v1";
  }

  @Test
  @DisplayName("Успешная регистрация пользователя")
  void successfulRegistrationUser() {
    UserAccountModel regParams = new UserAccountModel();
    regParams.setUserName(userName);
    regParams.setPassword(successPasswd);

    UserAccountResponseModel response = step("Make request" , () ->
        given(registrRequestSpec)
            .body(regParams)
        .when()
            .post()
        .then()
            .spec(registrResponseSpec)
            .extract().as(UserAccountResponseModel.class));

    step("Check response", ()-> {
        assertEquals(regParams.getUserName(), response.getUsername());
        assertNotNull(response.getUserID());
        assertEquals(0, response.getBooks().length);
    });
  }

  @Test
  @DisplayName("Регистрация пользователя без пароля")
  void regUserWithoutPassword400() {
    UserAccountModel regParams = new UserAccountModel();
    regParams.setUserName(userName);
    regParams.setPassword("");

    ErrorResponseModel response = step("Make request" , () ->
        given(registrRequestSpec)
            .body(regParams)
        .when()
            .post()
        .then()
            .spec(unregistrResponseSpec)
            .extract().as(ErrorResponseModel.class));

    step("Check response", ()->
        assertEquals("UserName and Password required.", response.getMessage()));
  }

  @Test
  @DisplayName("Регистрация пользователя с некорректным паролем - недостаток символов")
  void regUserPasswordNotEnoughChar400() {
    UserAccountModel regParams = new UserAccountModel();
    regParams.setUserName(userName);
    regParams.setPassword(errorPasswdLength);

    ErrorResponseModel response = step("Make request" , () ->
        given(registrRequestSpec)
            .body(regParams)
        .when()
            .post()
        .then()
            .spec(unregistrResponseSpec)
            .extract().as(ErrorResponseModel.class));

    step("Check response", ()->
        assertEquals("Passwords must have at least one non alphanumeric character, "
        + "one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), "
        + "one special character and Password must be eight characters or longer.", response.getMessage()));
  }

  @Test
  @DisplayName("Регистрация пользователя с некорректным паролем - нет цифры")
  void regUserPasswordNotDigit400() {
    UserAccountModel regParams = new UserAccountModel();
    regParams.setUserName(userName);
    regParams.setPassword(errorPasswdDigit);

    ErrorResponseModel response = step("Make request" , () ->
        given(registrRequestSpec)
            .body(regParams)
        .when()
            .post("https://demoqa.com/Account/v1/User")
        .then()
            .spec(unregistrResponseSpec)
            .extract().as(ErrorResponseModel.class));

    step("Check response", ()->
        assertEquals("Passwords must have at least one non alphanumeric character, "
        + "one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), "
        + "one special character and Password must be eight characters or longer.", response.getMessage()));
  }

  @Test
  @DisplayName("Успешное создание токена пользователя")
  void successfulGenerateUserToken() {
    UserAccountModel regParams = new UserAccountModel();
    regParams.setUserName(userName);
    regParams.setPassword(successPasswd);

    step("Make registration request" , () ->
        given(registrRequestSpec)
            .body(regParams)
        .when()
            .post()
        .then()
            .spec(registrResponseSpec));

    TokenModel response = step("Make token request", () ->
        given(tokenRequestSpec)
            .body(regParams)
        .when()
            .post()
        .then()
            .spec(tokenResponseSpec)
            .extract().as(TokenModel.class));

    step("Check response", ()-> {
      assertNotNull(response.getToken());
      assertNotNull(response.getExpires());
      assertEquals("Success", response.getStatus());
      assertEquals("User authorized successfully.", response.getResult());
    });
  }
}
