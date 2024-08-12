package models;

import lombok.Data;

@Data
public class UserAccountResponseModel {

  String userID, username;
  String[] books;
}
