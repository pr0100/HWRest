package tests.API;

import static io.restassured.RestAssured.given;
import static tests.TestData.defaultLogin;
import static tests.TestData.defaultPassword;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.UserAccountModel;

public class AuthorizationApi {

  private UserAccountModel getParams() {
    UserAccountModel regParams = new UserAccountModel();
    regParams.setUserName(defaultLogin);
    regParams.setPassword(defaultPassword);
    return regParams;
  }

  @Step
  public Response getLoginResponse() {
    return given()
          .contentType("application/json")
          .body(getParams())
        .when()
          .post("/Account/v1/Login")
        .then()
          .log().all()
          .statusCode(200)
          .extract()
          .response();
  }

  @Step
  public Response getTokenResponse() {
    return given()
          .contentType("application/json")
          .body(getParams())
        .when()
          .post("/Account/v1/GenerateToken")
        .then()
          .log().all()
          .statusCode(200)
          .extract()
          .response();
  }
}
