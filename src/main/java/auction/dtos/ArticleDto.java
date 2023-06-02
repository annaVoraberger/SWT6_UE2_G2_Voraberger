package auction.dtos;

import auction.domain.ArticleStatus;
import auction.domain.Bid;
import auction.domain.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
public class ArticleDto {
  private Long id;

  private String name;

  private String description;

  private Double reservePrice;

  private Double hammerPrice;

  private LocalDate auctionStartDate;

  private LocalDate auctionEndDate;

  private Double highestBid;

  public void setHighestBid(Bid highestBid) {
    this.highestBid = highestBid.getBid();
  }

  private String sellerName;

  private ArticleStatus status;
}
