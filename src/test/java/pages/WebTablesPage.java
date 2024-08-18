package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import org.openqa.selenium.Keys;

public class WebTablesPage {
  private SelenideElement elementsPage = $("div.home-body > div > div:nth-child(1)"),
      webTablesPage = $("#item-3"),
      newRecordBtn = $("#addNewRecordButton"),
      inputFirstName = $("#firstName"),
      inputLastName = $("#lastName"),
      inputMail = $("#userEmail"),
      inputAge = $("#age"),
      inputSalary = $("#salary"),
      inputDepartment = $("#department"),
      submitBtn = $("#submit"),
      registrForm = $("#registration-form-modal");

  public WebTablesPage openElementsPage() {
    elementsPage.click();
    return this;
  }

  public WebTablesPage openWebPage() {
    webTablesPage.click();
    return this;
  }

  public WebTablesPage addNewRecord() {
    newRecordBtn.click();
    return this;
  }

  public WebTablesPage setFirstName(String value) {
    inputFirstName.setValue(value);
    return this;
  }

  public WebTablesPage setLastName(String value) {
    inputLastName.setValue(value);
    return this;
  }

  public WebTablesPage setMail(String value) {
    inputMail.setValue(value);
    return this;
  }

  public WebTablesPage setAge(String value) {
    inputAge.setValue(value);
    return this;
  }

  public WebTablesPage clearAge() {
    inputAge.clear();
    return this;
  }

  public WebTablesPage setSalary(String value) {
    inputSalary.setValue(value);
    return this;
  }

  public WebTablesPage setDepartment(String value) {
    inputDepartment.setValue(value);
    return this;
  }

  public WebTablesPage submitBtn() {
    submitBtn.click();
    return this;
  }

  public WebTablesPage checkNewRecord(String value) {
    $$(".rt-tbody .rt-tr-group").findBy(text(value)).shouldBe(visible);
    return this;
  }

  public WebTablesPage checkRegistrFormOpen() {
    assert(registrForm.isDisplayed());
    return this;
  }

  public WebTablesPage sendKeyEscape() {
    inputFirstName.sendKeys(Keys.ESCAPE);
    return this;
  }

  public WebTablesPage checkRegistrFormClose() {
    registrForm.shouldNotBe(visible, Duration.ofSeconds(1));
    assertFalse(registrForm.isDisplayed());
    return this;
  }

  public WebTablesPage openEditForm(String value) {
    $x("//div[contains(@class, 'rt-tbody')]//div[text()='" + value + "']"
        + "/ancestor::div[contains(@class, 'rt-tr')]//span[@title='Edit']").click();
    return this;
  }


  public WebTablesPage checkEditAgeInRecord(String valueName, String valueAge) {
    $x("//div[contains(@class, 'rt-tbody')]//div[text()='" + valueName + "']"
        + "/ancestor::div[contains(@class, 'rt-tr')]//div[3]").shouldHave(text(valueAge));
    return this;
  }

}
