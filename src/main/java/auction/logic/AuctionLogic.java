package auction.logic;

import auction.daos.ArticleManager;
import auction.domain.Article;
import auction.domain.ArticleStatus;
import auction.domain.Bid;
import auction.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface AuctionLogic {
  List<Article> getAllArticles();
  List<Article> getArticlesByState(ArticleStatus status);
  Article getArticleById(Long id);
  boolean addArticle(Article article);
  boolean addBid(Bid bid, Long customerId, Long articleId);
  Bid getHighestBidOnArticle(Article article);
  boolean authenticateCustomer(String customerEmail);
}
