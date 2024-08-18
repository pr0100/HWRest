package tests;

import com.github.javafaker.Faker;

public class TestData {

  private static Faker faker = new Faker();
  public static String firstName = faker.name().firstName();
  public static String lastName = faker.name().lastName();
  public static String fullName = faker.name().fullName();
  public static String email = faker.internet().emailAddress();
  public static String age = String.valueOf(faker.number().numberBetween(18, 80));
  public static String salary = String.valueOf(faker.number().numberBetween(1000, 10000));
  public static String department = faker.company().name();
  public static String rndLastName = getRandomName();
  public static String address = faker.address().fullAddress();
  //public static String userName = faker.name().username();
  //public static String successPasswd = "a!1" + faker.internet().password(8, 15, true, true, true);
  public static String errorPasswdLength = faker.internet().password(1, 5);
  public static String errorPasswdDigit = faker.internet().password(8, 9, true, true, false);
  public static String elemArrayBooks = String.valueOf(faker.number().numberBetween(0, 7));
  public static String wrongIsbn = String.valueOf(faker.number().numberBetween(10, 12));

  private static String getRandomName() {
    String[] names = {"Vega", "Cantrell", "Gentry"};
    return getRandomItemFromArray(names);
  }

  private static String getRandomItemFromArray(String[] array){
    int index = faker.number().numberBetween(0, 2);
    return array[index];
  }

  public static String getSuccessfulUserName() {
    return faker.name().username();
  }

  public static String getSuccessfulPasswd() {
    return "a!1" + faker.internet().password(8, 15, true, true, true);
  }

}
