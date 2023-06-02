import auction.daos.BidManager;
import auction.daos.repos.BidRepository;
import auction.domain.Article;
import auction.domain.Bid;
import auction.domain.Customer;
import auction.util.JpaUtil;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
//@SpringBootTest
@Ignore
public class BidManagerTest {

  @Autowired
  private BidRepository bidRepository;

  @Autowired
  private BidManager bidManager;


  //Test
  @Ignore
  public void testGetBidById() {
    Long bidId = 1L;
    Bid bid = new Bid();
    bid.setId(bidId);

    Optional<Bid> result = bidManager.getBidById(bidId);

    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(bidId, result.get().getId());
  }

  //Test
  @Ignore
  public void testGetBidsOnArticle() {
    Article article = new Article();
    List<Bid> bids = new ArrayList<>();
    bids.add(new Bid());
    bids.add(new Bid());


    List<Bid> result = bidManager.getBidsOnArticle(article);

    Assertions.assertEquals(bids.size(), result.size());


  }

  //Test
  @Ignore
  public void testGetBidsByCustomer() {
    Customer customer = new Customer();
    List<Bid> bids = new ArrayList<>();
    bids.add(new Bid());
    bids.add(new Bid());


    List<Bid> result = bidManager.getBidsByCustomer(customer);

    Assertions.assertEquals(bids.size(), result.size());

  }

  //Test
  @Ignore
  public void testGetHighestBidOnArticle() {
    Article article = new Article();
    Bid highestBid = new Bid();


    Optional<Bid> result = bidManager.getHighestBidOnArticle(article);

    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(highestBid, result.get());

  }

  //Test
  @Ignore
  public void testInsertBid() {
    Bid bid = new Bid();


    Bid result = bidManager.insert(bid);

    Assertions.assertEquals(bid, result);

  }

  //Test
  @Ignore
  public void testUpdateBid() {
    Bid bid = new Bid();


    Bid result = bidManager.update(bid);

    Assertions.assertEquals(bid, result);


  }

  //Test
  @Ignore
  public void testDeleteBid() {
    Bid bid = new Bid();
    Article article = new Article();
    article.addBid(bid);

    bidManager.delete(bid);


    Assertions.assertFalse(article.getBids().contains(bid));
  }
}