package tests;

import static io.restassured.RestAssured.*;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;

import models.BookRequestModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты методов BookStore")
public class BookTests {

  private static final Faker faker = new Faker();
  private final String elemArrayBooks = String.valueOf(faker.number().numberBetween(0, 7));
  private final String wrongIsbn = String.valueOf(faker.number().numberBetween(10, 12));

  @BeforeAll
  public static void setUp()  {
    baseURI = "https://demoqa.com/BookStore/v1";
    requestSpecification = new RequestSpecBuilder()
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
