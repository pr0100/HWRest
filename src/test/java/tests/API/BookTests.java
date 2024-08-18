package tests.API;

import static io.restassured.RestAssured.*;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.TestData.*;

import models.BookRequestModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты для методов BookStore")
public class BookTests {

  @BeforeAll
  public static void setUp()  {
    baseURI = "https://demoqa.com/BookStore/v1";
    requestSpecification = new RequestSpecBuilder()
        .addFilter(new AllureRestAssured())
        .log(LogDetail.ALL)
        .build();
    responseSpecification = new ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .build();
  }

  @Test
  @DisplayName("Поиск всех книг")
  void successfulBooksSearch() {
    Response response = get("/Books");
    int booksSize = response.path("books.size()");
    assertEquals(8, booksSize);
  }

  @Test
  @DisplayName("Успешный поиск книги с определенным ISBN")
  void successfulBookSearch() {
    Response responseAllBooks = get("/Books");
    String paramIsbn = responseAllBooks.path("books[" + elemArrayBooks + "].isbn");

    BookRequestModel getParam = new BookRequestModel();
    getParam.setIsbn(paramIsbn);
    Response responseOneBooks = get("/Book?ISBN=" + getParam.getIsbn());

    String isbn = responseOneBooks.path("isbn");
    assertEquals(getParam.getIsbn(), isbn);
  }

  @Test
  @DisplayName("Поиск книги, которой нет в Books Store")
  void bookSearchNotFound() {
    Response response = get("/Book?ISBN=" + wrongIsbn);

    String message = response.path("message");
    assertEquals("ISBN supplied is not available in Books Collection!", message);
    assertEquals(400, response.statusCode());
  }
}
