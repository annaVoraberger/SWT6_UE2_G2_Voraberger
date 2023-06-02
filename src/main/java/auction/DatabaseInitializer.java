package auction;

import auction.daos.ArticleManager;
import auction.daos.CustomerManager;
import auction.domain.Article;
import auction.domain.Bid;
import auction.domain.Customer;
import auction.logic.AuctionLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

  @Autowired
  private AuctionLogic logic;
  @Autowired
  private ArticleManager artMan;
  @Autowired
  private CustomerManager custMan;

  public DatabaseInitializer(){
    log.info("Created");
  }

  @Override
  public void run(String... args) {
    log.info("Initializing Database with test data");
    Article a1 = new Article("Schrank","Antiker Schrank aus Eichenholz, neu restauriert", 130.40, LocalDate.of(2018, 3, 1));
    Customer c1 = new Customer("Anna","Voraberger", "email@email.com");
    Bid b1 = new Bid(150.0, LocalDate.of(2019,10,10), a1, c1);
    artMan.insert(a1);
    custMan.insert(c1);
    logic.addBid(b1, a1.getId(), c1.getId());

    Article a2 = new Article("Schrank2","Antiker Schrank aus Eichenholz, neu restauriert", 130.40, LocalDate.of(2018, 3, 1));
    Customer c2 = new Customer("Anna2","Voraberger", "email@email.com");
    Bid b2 = new Bid(160.0, LocalDate.of(2019,10,10), a1, c1);
    artMan.insert(a2);
    custMan.insert(c2);
    logic.addBid(b2, a2.getId(), c2.getId());
  }
}
