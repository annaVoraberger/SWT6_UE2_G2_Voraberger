package auction.dtos;

import auction.domain.Article;
import auction.domain.Customer;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Getter
@Setter
public class BidPostDto {

  private Long id;

  private Double bid;

  private Date date;

  private Long articleId;

  private Long customerId;
}
