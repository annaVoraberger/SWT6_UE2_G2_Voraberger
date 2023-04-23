package auction.managers;

import lombok.NoArgsConstructor;
import auction.domain.Article;
import auction.domain.Bid;
import auction.domain.Customer;

import java.util.List;

@NoArgsConstructor
public class BidManager {

  public static Bid getBidById(){
    return null;
  }

  public static List<Bid> getBidsOnArticle(Article article){
    return null;
  }

  public static List<Bid> getBidsByCustomer(Customer customer){
    return null;
  }

  public static Bid getHighestBidPOnArticle(Article article){
    return null;
  }

  public static boolean insert(Bid bid){
    return false;
  }

  public static boolean update(Bid bid){
    return false;
  }

  public static boolean delete(Bid bid){
    return false;
  }
}
