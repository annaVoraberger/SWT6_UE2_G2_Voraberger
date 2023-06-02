package auction.daos;

import jakarta.persistence.EntityManager;

public interface EntityManagerTask {

  void execute(EntityManager em);
}