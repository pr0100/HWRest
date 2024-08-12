package models;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class TokenModel {
    String token, expires, status, result;
}
