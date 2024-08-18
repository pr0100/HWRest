package tests.UI;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.Keys;

public class WebTablesSteps {

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

  @Step
  public void openElementsPage() {
    elementsPage.click();
  }

  @Step
  public void openWebPage() {
    webTablesPage.click();
  }

  @Step
  public void addNewRecord() {
    newRecordBtn.click();
  }

  @Step
  public void setFirstName(String value) {
    inputFirstName.setValue(value);
  }

  @Step
  public void setLastName(String value) {
    inputLastName.setValue(value);
  }

  @Step
  public void setMail(String value) {
    inputMail.setValue(value);
  }

  @Step
  public void setAge(String value) {
    inputAge.setValue(value);
  }

  @Step
  public void clearAge() {
    inputAge.clear();
  }

  @Step
  public void setSalary(String value) {
    inputSalary.setValue(value);
  }

  @Step
  public void setDepartment(String value) {
    inputDepartment.setValue(value);
  }

  @Step
  public void submitBtn() {
    submitBtn.click();
  }

  @Step
  public void checkNewRecord(String value) {
    $$(".rt-tbody .rt-tr-group").findBy(text(value)).shouldBe(visible);
  }

  @Step
  public void checkRegistrFormOpen() {
    assert(registrForm.isDisplayed());
  }

  @Step
  public void sendKeyEscape() {
    inputFirstName.sendKeys(Keys.ESCAPE);
  }

  @Step
  public void checkRegistrFormClose() {
    registrForm.shouldNotBe(visible, Duration.ofSeconds(1));
    assertFalse(registrForm.isDisplayed());
  }

  @Step
  public void openEditForm(String value) {
    $x("//div[contains(@class, 'rt-tbody')]//div[text()='" + value + "']"
        + "/ancestor::div[contains(@class, 'rt-tr')]//span[@title='Edit']").click();
  }

  @Step
  public void checkEditAgeInRecord(String valueName, String valueAge) {
    $x("//div[contains(@class, 'rt-tbody')]//div[text()='" + valueName + "']"
        + "/ancestor::div[contains(@class, 'rt-tr')]//div[3]").shouldHave(text(valueAge));
  }



}
