package auction.logic;

import auction.daos.ArticleManager;
import auction.daos.BidManager;
import auction.daos.CustomerManager;
import auction.domain.Article;
import auction.domain.ArticleStatus;
import auction.domain.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Insights {

  private static ArticleManager articleManager = new ArticleManager();
  private static BidManager bidManager = new BidManager();
  private static CustomerManager customerManager = new CustomerManager();



  /***
   * @param searchPhrase a phrase which should be contained in the Article description
   * @param maxReservePrice only Articles with a price lower than maxReservePrice are searched for
   *                        not considered if null or < 0
   * @param order if order != null, returned articles will be in the requested order
   * @return
   *    List of Articles that include searchPhrase in their description
   *        and have a reservePrice under the max, in the requested order
   */
  public static List<Article> findArticlesByDescription(String searchPhrase, Double
          maxReservePrice, ArticleOrder order) {

    if (order != null){
      switch (order) {
        case NAME:
           return findArticlesByDescriptionAndMaxReservePrice(searchPhrase, maxReservePrice)
                   .stream().sorted(Comparator.comparing(Article::getName)).toList();
        case HAMMER_PRICE:
          return findArticlesByDescriptionAndMaxReservePrice(searchPhrase, maxReservePrice)
                  .stream().sorted(Comparator.comparingDouble(Article::getHammerPrice)).toList();
        case RESERVE_PRICE:
          return findArticlesByDescriptionAndMaxReservePrice(searchPhrase, maxReservePrice)
                  .stream().sorted(Comparator.comparingDouble(Article::getReservePrice)).toList();
        case AUCTION_START_DATE:
          return findArticlesByDescriptionAndMaxReservePrice(searchPhrase, maxReservePrice)
                  .stream().sorted(Comparator.comparing(Article::getAuctionStartDate)).toList();
      }
    } else {
      if (maxReservePrice != null && maxReservePrice < 0){
        return findArticlesByDescriptionAndMaxReservePrice(searchPhrase, maxReservePrice);
      }
    }

    return null;
  }

  private static List<Article> findArticlesByDescriptionAndMaxReservePrice(String searchPhrase, Double maxReservePrice){
    return articleManager.getArticles().stream()
            .filter(a -> a.getDescription().contains(searchPhrase))
            .filter(a -> a.getReservePrice()<= maxReservePrice).toList();
  }

  /***
   * @param id of the Article required
   * @return
   *    null if the Auction was not started yet
   *    null if the Auction is over and the article was not sold
   *    the HAMMER_PRICE, if the Article was already sold
   *    the highest Bid if the Auction is active
   * @throws ArticleNotFoundException if the Article with the given Id does not exist
   */
  public static Double getArticlePrice(Long id) throws ArticleNotFoundException {
    Article article = articleManager.getArticleById(id);
    if (article == null){
      return null;
    }
    if (LocalDate.now().compareTo(article.getAuctionEndDate()) < 0){
      return null;
    }
    if (article.getHammerPrice() > 0){
      return article.getHammerPrice();
    }
    if(true){
      throw new ArticleNotFoundException();
    } else return Double.POSITIVE_INFINITY;
  }

  /***
   * @param count sellers (Customer)
   * @return with the most revenue (HAMMER_PRICE) in a List
   */
  public static List<Customer> getTopSellers(int count){
    //TODO
    return new ArrayList<>();
  }


  /***
   * @param count top articles
   * @return which had the biggest price gap between reservePrice and HammerPrice
   */
  public static List<Article> getTopArticles(int count){
    //TODO
    return new ArrayList<>();

  }

}
