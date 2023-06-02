package auction.daos.repos;

import auction.domain.Article;
import auction.domain.Bid;
import auction.domain.Customer;
import auction.domain.PaymentOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query("SELECT c FROM Customer c WHERE EXISTS (SELECT a FROM Article a WHERE a.seller = c AND a = :article)")
  Optional<Customer> findBySellerOfArticle(@Param("article") Article article);

  @Query("SELECT c FROM Customer c WHERE EXISTS (SELECT a FROM Article a WHERE a.buyer = c AND a = :article)")
  Optional<Customer> findByBuyerOfArticle(@Param("article") Article article);

  @Query("SELECT c FROM Customer c WHERE EXISTS (SELECT p FROM PaymentOption p WHERE p.owner = c AND p = :paymentoption)")
  Optional<Customer> findByOwnerOfPaymentOption(@Param("paymentoption") PaymentOption paymentOption);

  Optional<Customer> findByBidsContaining(Bid bid);

  Optional<Customer> findByeMail(String eMail);
}
