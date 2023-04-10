package swt6.auction.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

  private Date auctionStartDate;

  private Date auctionEndDate;

  @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
  private Set<Bid> bids = new HashSet<>();

  private ArticleStatus status;
}
