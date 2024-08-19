package models;

import java.util.List;
import lombok.Data;

@Data
public class AddBookResponseModel {
  List<IsbnModel> books;
}
