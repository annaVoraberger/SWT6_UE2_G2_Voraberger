package swt6.auction.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bankaccount extends PaymentOption{

  private String owner;

  private Long IBAN;

  private String BIC;
}
