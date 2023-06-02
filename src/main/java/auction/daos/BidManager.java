package auction.daos;


import auction.daos.repos.BidRepository;
import auction.domain.Article;
import auction.domain.Bid;
import auction.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Component
public class BidManager {

  @Autowired
  private BidRepository bidRepository;

  public Optional<Bid> getBidById(Long id){
    return bidRepository.findById(id);
  }

  public List<Bid> getBidsOnArticle(Article article){
    return bidRepository.findAllByArticle(article);
  }

  public List<Bid> getBidsByCustomer(Customer customer){
    return bidRepository.findAllByBidder(customer);
  }

  public Optional<Bid> getHighestBidOnArticle(Article article){
    return bidRepository.findHighestBidOnArticle(article);
  }

  public Bid insert(Bid bid){
    return bidRepository.save(bid);
  }

  public Bid update(Bid bid){
    return bidRepository.save(bid);
  }

  public void delete(Bid bid){
    bid.getBidder().removeBid(bid);
    bid.getArticle().removeBid(bid);
    bidRepository.delete(bid);
  }
}