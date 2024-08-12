package specs;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.builder.ResponseSpecBuilder;

public class BooksSpec {
  public static ResponseSpecification booksResponseSpec = new ResponseSpecBuilder()
      .expectStatusCode(200)
      .log(STATUS)
      .log(BODY)
      .build();
}
