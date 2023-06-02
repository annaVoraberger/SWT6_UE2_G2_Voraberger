package auction.logic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends Exception{
  public ArticleNotFoundException() {
    System.out.println("Article not found");
  }
}
