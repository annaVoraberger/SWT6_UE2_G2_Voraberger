package auction.daos.repos;

import auction.domain.Article;
import auction.domain.Bid;
import auction.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

  List<Bid> findAllByArticle(Article article);

  List<Bid> findAllByBidder(Customer customer);

  @Query("SELECT b FROM Bid b WHERE b.bid = (SELECT MAX(b2.bid) FROM Bid b2 WHERE b2.article = :article)")
  Optional<Bid> findHighestBidOnArticle(@Param("article") Article article);



}
