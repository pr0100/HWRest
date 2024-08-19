package tests.API;

import static io.restassured.RestAssured.given;
import static tests.TestData.defaultLogin;
import static tests.TestData.defaultPassword;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.UserAccountModel;

public class AuthorizationApi {

  @Step
  public Response getLoginResponse() {
    UserAccountModel regParams = new UserAccountModel();
    regParams.setUserName(defaultLogin);
    regParams.setPassword(defaultPassword);

    return given()
            .contentType("application/json")
            .body(regParams)
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
    UserAccountModel regParams = new UserAccountModel();
    regParams.setUserName(defaultLogin);
    regParams.setPassword(defaultPassword);

    return given()
            .contentType("application/json")
            .body(regParams)
            .when()
            .post("/Account/v1/GenerateToken")
            .then()
            .log().all()
            .statusCode(200)
            .extract()
            .response();
  }
}
