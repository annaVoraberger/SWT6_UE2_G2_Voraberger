package auction.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

public class JpaUtil {
  public static EntityManager getEntityManager(EntityManagerFactory entityManagerFactory) {
    if (entityManagerFactory == null) return null;
    return EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
  }
  public static <T> T getJpaRepository(EntityManagerFactory entityManagerFactory,
                                       Class<T> entityType) {
    EntityManager em = getEntityManager(entityManagerFactory);
    JpaRepositoryFactory repoFactory = new JpaRepositoryFactory(em);
    return repoFactory.getRepository(entityType);
  }
}
