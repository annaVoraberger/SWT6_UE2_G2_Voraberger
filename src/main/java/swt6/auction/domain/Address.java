package swt6.auction.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
  private String street;
  private int houseNr;
  private int zipCode;
  private String town;
}
