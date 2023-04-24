package auction.managers;

import auction.util.TransactionsUtil;
import lombok.NoArgsConstructor;
import auction.domain.Article;
import auction.logic.ArticleOrder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ArticleManager {

  public Article getArticleById(Long id){
    if (id == null) return null;
    var result = TransactionsUtil.executeTaskWithResult(em ->{
      var query = em.createQuery("select a from Article a where id = :id",Article.class);
      query.setParameter("id",  id);
      return query.getSingleResult();
    });
    return result;
  }

  public List<Article> getArticles(){
    var result = TransactionsUtil.executeTaskWithResult(em ->{
      var query = em.createQuery("select a from Article a",Article.class);
      return query.getResultList();
    });
    return result;
  }

  public List<Article> getArticlesMaxReservePrice(Double maxReservePrice){
    if(maxReservePrice == null || maxReservePrice < 0){
      return new ArrayList<>();
    }
    var result = TransactionsUtil.executeTaskWithResult(em ->{
      var query = em.createQuery("select a from Article a where reservePrice <= :max",Article.class);
      query.setParameter("max",  maxReservePrice);
      return query.getResultList();
    });
    return result;
  }

  public List<Article> getArticlesMaxReservePriceInOrder(Double maxReservePrice, ArticleOrder order){
    final var max = (maxReservePrice != null && maxReservePrice > 0) ? maxReservePrice : Double.MAX_VALUE;
    switch (order) {
      case NAME: {
        var result = TransactionsUtil.executeTaskWithResult(em -> {
          var query = em.createQuery("select a from Article a where reservePrice <= :max order by name", Article.class);
          query.setParameter("max", max);
          return query.getResultList();
        });// Transaction end
        return result;
      }
      case HAMMER_PRICE: {
        var result = TransactionsUtil.executeTaskWithResult(em -> {
          var query = em.createQuery("select a from Article a where reservePrice <= :max order by hammerPrice desc", Article.class);
          query.setParameter("max", max);
          return query.getResultList();
        });// Transaction end
        return result;
      }
      case AUCTION_START_DATE: {
        var result = TransactionsUtil.executeTaskWithResult(em -> {
          var query = em.createQuery("select a from Article a where reservePrice <= :max order by auctionStartDate desc", Article.class);
          query.setParameter("max", max);
          return query.getResultList();
        });// Transaction end
        return result;
      }
      case RESERVE_PRICE: {
        var result = TransactionsUtil.executeTaskWithResult(em -> {
          var query = em.createQuery("select a from Article a where reservePrice <= :max order by reservePrice desc", Article.class);
          query.setParameter("max", max);
          return query.getResultList();
        });// Transaction end
        return result;
      }
    }
    var result = TransactionsUtil.executeTaskWithResult(em -> {
      var query = em.createQuery("select a from Article a where reservePrice <= :max", Article.class);
      query.setParameter("max", max);
      return query.getResultList();
    });// Transaction end
    return result;
  }

  public boolean insert(Article article){
    try {
      TransactionsUtil.execute(em -> {em.persist(article);});
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean update(Article article){
    if (article == null || getArticleById(article.getId()) == null){
      return false;
    }
    try {
      TransactionsUtil.execute(em -> {em.merge(article);});
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }



  public boolean delete(Article article){
    if (article == null){
      return false;
    }
    try {
      TransactionsUtil.execute(em -> {
        var query = em.createQuery("delete from Article a where a.id = :id", Article.class);
        query.setParameter("id", article.getId());
        query.executeUpdate();
      });
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

}
