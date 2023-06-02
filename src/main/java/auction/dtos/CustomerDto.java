package auction.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomerDto {
  private Long id;
  private String eMail;
}
