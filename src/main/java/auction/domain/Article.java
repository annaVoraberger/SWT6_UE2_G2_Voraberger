package auction.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Article {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private String description;

  private Double reservePrice;

  private Double hammerPrice;

  private LocalDate auctionStartDate;

  private LocalDate auctionEndDate;

  @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private Set<Bid> bids = new HashSet<>();

  private ArticleStatus status;

  @ManyToOne
  private Customer buyer;

  @ManyToOne
  private Customer seller;


  public void addBid(Bid bid){
    if (bid.getArticle() != null){
      //remove bid from other article
      bid.getArticle().bids.remove(bid);
    }
    bids.add(bid);
    bid.setArticle(this);
  }

  public void removeBid(Bid bid){
    if (bids.remove(bid)){
      bid.setArticle(null);
    }
  }

}
