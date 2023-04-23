package auction.managers;

import lombok.NoArgsConstructor;
import auction.domain.Article;
import auction.domain.Bid;
import auction.domain.Customer;
import auction.domain.PaymentOption;

import java.util.List;

@NoArgsConstructor
public class CustomerManager {
  public static Customer getCustomerById(){
    return null;
  }

  public static List<Customer> getCustomers(){
    return null;
  }

  public static Customer getSellerOf(Article article){
    return null;
  }

  public static Customer getBuyerOf(Article article){
    return null;
  }

  public static Customer getBidderOf(Bid bid){
    return null;
  }

  public static Customer getOwnerOf(PaymentOption paymentOption){
    return null;
  }


  public static boolean insert(Customer customer){
    return false;
  }

  public static boolean update(Customer customer){
    return false;

  }

  public static boolean delete(Customer customer){
    return false;

  }
}
