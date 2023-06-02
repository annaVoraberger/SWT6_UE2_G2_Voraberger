package auction.daos;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface EntityManagerTaskWithResult<T> {
  T execute(EntityManager em);
}
