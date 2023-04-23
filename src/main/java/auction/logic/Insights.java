package auction.logic;

import auction.domain.Article;
import auction.domain.Customer;
import auction.managers.ArticleManager;
import auction.managers.BidManager;
import auction.managers.CustomerManager;

import java.util.ArrayList;
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
    //TODO
    if (order != null){
      switch (order) {
        case NAME:
          //do something
        case HAMMER_PRICE:
          //do
        case RESERVE_PRICE:
          //do
        case AUCTION_START_DATE:
          //do
      }
    } else {
      if (maxReservePrice != null && maxReservePrice < 0){
        List<Article> articles = findArticlesByDescriptionAndMaxReservePrice(searchPhrase, maxReservePrice);
        //TODO
      }
    }

    return null;
  }

  private static List<Article> findArticlesByDescriptionAndMaxReservePrice(String searchPhrase, Double maxReservePrice){
    return new ArrayList<>(); // TODO maxReserveprice definitely given
  }

  /***
   * @param Id of the Article required
   * @return
   *    null if the Auction was not started yet
   *    null if the Auction is over and the article was not sold
   *    the HAMMER_PRICE, if the Article was already sold
   *    the highest Bid if the Auction is active
   * @throws ArticleNotFoundException if the Article with the given Id does not exist
   */
  public static Double getArticlePrice(Article Id) throws ArticleNotFoundException {
    //TODO
    if(true){
      throw new ArticleNotFoundException(Id);
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
