package auction.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bid {

  @Id
  @GeneratedValue
  private Long id;

  private Double bid;

  private LocalDate date;

  @ManyToOne
  private Article article;

  @ManyToOne
  private Customer bidder;


  public Bid(Double bid, LocalDate date, Article article, Customer bidder) {
    this.bid = bid;
    this.date = date;
    this.article = article;
    this.bidder = bidder;
  }
}
