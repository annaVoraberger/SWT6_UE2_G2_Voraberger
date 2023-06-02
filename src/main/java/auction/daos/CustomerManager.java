package auction.daos;

import auction.daos.repos.CustomerRepository;
import lombok.NoArgsConstructor;
import auction.domain.Article;
import auction.domain.Bid;
import auction.domain.Customer;
import auction.domain.PaymentOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Component
public class CustomerManager {

  @Autowired
  private CustomerRepository customerRepository;

  public Optional<Customer> getCustomerById(Long id){
    return customerRepository.findById(id);
  }
  public Optional<Customer> getCustomerByEMail(String eMail){
    return customerRepository.findByeMail(eMail);
  }

  public List<Customer> getCustomers(){
    return customerRepository.findAll();
  }

  public Optional<Customer> getSellerOf(Article article){
    return customerRepository.findBySellerOfArticle(article);
  }

  public Optional<Customer> getBuyerOf(Article article){
    return customerRepository.findByBuyerOfArticle(article);
  }

  public Optional<Customer> getBidderOf(Bid bid){
    return customerRepository.findByBidsContaining(bid);
  }

  public Optional<Customer> getOwnerOf(PaymentOption paymentOption){
    return customerRepository.findByOwnerOfPaymentOption(paymentOption);
  }


  public Customer insert(Customer customer){
    return customerRepository.save(customer);
  }

  public Customer update(Customer customer){
    return customerRepository.save(customer);
  }

  public void delete(Customer customer){
    customerRepository.delete(customer);
  }
}
