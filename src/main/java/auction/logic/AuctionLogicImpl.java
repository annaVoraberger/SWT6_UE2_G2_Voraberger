package auction.logic;

import auction.daos.ArticleManager;
import auction.daos.BidManager;
import auction.daos.CustomerManager;
import auction.domain.Article;
import auction.domain.ArticleStatus;
import auction.domain.Bid;
import auction.domain.Customer;
import auction.dtos.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service("auctionLogic")
public class AuctionLogicImpl implements AuctionLogic{
  @Autowired
  private ArticleManager articleMan;
  @Autowired
  private BidManager bidMan;
  @Autowired
  private CustomerManager customerMan;

  @Override
  public List<Article> getAllArticles() {
    return articleMan.getArticles();
  }

  @Override
  public List<Article> getArticlesByState(ArticleStatus status) {
    return articleMan.getArticlesByStatus(status);
  }

  @Override
  public Article getArticleById(Long id) {
    Article article = articleMan.getArticleById(id);
    if (article == null){
      return null;
    }
    return article;
  }

  @Override
  public boolean addBid(Bid bid, Long customerId, Long articleId) {
    Optional<Customer> optionalCustomer = customerMan.getCustomerById(customerId);
    if (optionalCustomer.isEmpty()) return false;
    Customer customer = optionalCustomer.get();

    Article article = articleMan.getArticleById(articleId);
    if (article == null) return false;

    bid.setArticle(article);
    bid.setBidder(customer);
    customer.addBid(bid);
    article.addBid(bid);

    if (bidMan.insert(bid) == bid) {
      customerMan.update(customer);
      articleMan.update(article);
      return true;
    }
    return false;
  }

  @Override
  public Bid getHighestBidOnArticle(Article article) {
    Optional<Bid> bidOpt = bidMan.getHighestBidOnArticle(article);
    if (bidOpt.isPresent()){
      return bidOpt.get();
    }
    return null;
  }

  @Override
  public boolean authenticateCustomer(String customerEmail) {
    return (customerMan.getCustomerByEMail(customerEmail).isPresent());
  }
}
