package specs;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.builder.ResponseSpecBuilder;

public class AccountSpec {

  public static RequestSpecification registrRequestSpec = with()
      .filter(new AllureRestAssured())
      .log().uri()
      .log().body()
      .log().headers()
      .contentType(JSON)
      .basePath("/User");

  public static RequestSpecification tokenRequestSpec = with()
      .filter(new AllureRestAssured())
      .log().uri()
      .log().body()
      .log().headers()
      .contentType(JSON)
      .basePath("/GenerateToken");

  public static ResponseSpecification registrResponseSpec = new ResponseSpecBuilder()
      .expectStatusCode(201)
      .log(STATUS)
      .log(BODY)
      .build();

  public static ResponseSpecification unregistrResponseSpec = new ResponseSpecBuilder()
      .expectStatusCode(400)
      .log(STATUS)
      .log(BODY)
      .build();

  public static ResponseSpecification tokenResponseSpec = new ResponseSpecBuilder()
      .expectStatusCode(200)
      .log(STATUS)
      .log(BODY)
      .build();
}
