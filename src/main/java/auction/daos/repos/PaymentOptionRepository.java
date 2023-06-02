package auction.daos.repos;

import auction.domain.PaymentOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOptionRepository extends JpaRepository<PaymentOption, Long> {
}
