package auction.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import auction.daos.EntityManagerTask;
import auction.daos.EntityManagerTaskWithResult;


public class TransactionsUtil {
  private static EntityManagerFactory emFactory;

  public static synchronized EntityManagerFactory getEntityManagerFactory() {
    if (emFactory == null)
      emFactory = Persistence.createEntityManagerFactory("AuctionPU");
    return emFactory;
  }

  public static synchronized void closeEntityManagerFactory() {
    if (emFactory != null) {
      emFactory.close();
      emFactory = null;
    }
  }

  public static EntityManager getTransactionalEntityManager() {
    var em = getEntityManagerFactory().createEntityManager();
    em.getTransaction().begin();
    return em;
  }

  public static void commit(EntityManager em) {
    var tx = em.getTransaction();
    if (tx.isActive()) tx.commit();
  }

  public static void rollback(EntityManager em) {
    var tx = em.getTransaction();
    if (tx.isActive()) tx.rollback();
  }

  public static void execute(EntityManagerTask task) {
    try (var em = TransactionsUtil.getTransactionalEntityManager()) {
      try{
        task.execute(em);
        TransactionsUtil.commit(em);
      } catch (Exception ex) {
        TransactionsUtil.rollback(em);
        throw ex;
      }
    }
  }

  public static <T> T executeTaskWithResult(EntityManagerTaskWithResult<T> task) {
    try (var em = TransactionsUtil.getTransactionalEntityManager()) {
      try {
        var result = task.execute(em);
        TransactionsUtil.commit(em);
        return result;
      } catch (Exception ex) {
        TransactionsUtil.rollback(em);
        throw ex;
      }
    }
  }
}
