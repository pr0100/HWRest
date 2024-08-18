package tests.UI;

import static tests.TestData.*;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.WebTablesPage;
import tests.TestBase;

@DisplayName("Тесты для страницы Web Tables")
public class WebTablesTests extends TestBase {

  WebTablesPage webTablesPage = new WebTablesPage();
  WebTablesSteps steps = new WebTablesSteps();
  protected static final Logger LOGGER = LogManager.getLogger();

  @Test
  @DisplayName("Добавление новой строки в таблицу при помощи формы")
  void successfulAddNewRecord() {
    steps.openElementsPage();
    steps.openWebPage();
    steps.addNewRecord();
    steps.setFirstName(firstName);
    steps.setLastName(lastName);
    steps.setMail(email);
    steps.setAge(age);
    steps.setSalary(salary);
    steps.setDepartment(department);
    steps.submitBtn();
    steps.checkNewRecord(firstName);

    LOGGER.info("New record added");
  }

  @Test
  @DisplayName("Форма регистрации осталась открытой из-за незаполнения обязательных полей")
  void registrationFormNotClose() {
    SelenideLogger.addListener("allure", new AllureSelenide());
    webTablesPage.openElementsPage()
        .openWebPage()
        .addNewRecord()
        .setFirstName(firstName)
        .submitBtn()
        .checkRegistrFormOpen();

    LOGGER.info("Registration Form still open");
  }

}
