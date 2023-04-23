package auction.logic;

import auction.domain.Article;

public class ArticleNotFoundException extends Exception{
  public ArticleNotFoundException(Article article) {
    System.out.println("Article not found");
  }
}
