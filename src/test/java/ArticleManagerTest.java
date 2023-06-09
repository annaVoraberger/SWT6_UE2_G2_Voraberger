import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import auction.domain.Article;
import auction.logic.ArticleOrder;
import auction.daos.ArticleManager;
import auction.util.TransactionsUtil;


import java.time.LocalDate;
import java.util.List;

@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArticleManagerTest {

  static ArticleManager articleManager = new ArticleManager();

  @BeforeClass
  public static void init(){
    TransactionsUtil.getEntityManagerFactory();
  }


  @AfterClass
  public static void cleanup(){
    TransactionsUtil.closeEntityManagerFactory();
  }

  @Ignore
  //Test
  public void getArticleById_returnsCorrectArticle(){
    Article article = articleManager.getArticleById((long)1);
    Assert.assertEquals(article.getId(), (long)1);
  }

  @Ignore
  //Test
  public void getArticles_returnsAllArticles(){
    List<Article> articles = articleManager.getArticles();
    Assert.assertEquals(articles.size(), 6); //TODO

  }
  /*
  // Successful with empty DB
  //Test
  public void getArticles_EmptyDataBase_returnsEmptyList(){
    List<Article> articles = articleManager.getArticles();
    Assert.assertEquals(articles.size(), 0); //TODO
  }*/

  @Ignore
  //Test
  public void getArticlesMaxReservePrice_returnsCorrectArticles() {
    List<Article> articles = articleManager.getArticlesMaxReservePrice(100.0);
    Assert.assertEquals(articles.size(), 3); //TODO
    Assert.assertTrue(articles.get(0).getReservePrice()<=100.0);
  }

  @Ignore
  //Test
  public void getArticlesMaxReservePrice_MaxReservePriceZero_ReturnsEmptyList(){
    List<Article> articles = articleManager.getArticlesMaxReservePrice(0.0);
    Assert.assertEquals(articles.size(), 0);
  }

  @Ignore
  //Test
  public void getArticlesMaxReservePrice_MaxReservePriceNull_ReturnsEmptyList(){
    List<Article> articles = articleManager.getArticlesMaxReservePrice(null);
    Assert.assertEquals(articles.size(), 0);
  }

  @Ignore
  //Test
  public void getArticlesMaxReservePriceInOrder_NameOrder_ReturnsCorrectList(){
    List<Article> articles = articleManager.getArticlesMaxReservePriceInOrder(100.0, ArticleOrder.NAME);
    Assert.assertEquals(articles.get(0).getName(), "AName");
    Assert.assertEquals(articles.get(2).getName(), "ZName");
  }

  @Ignore
  //Test
  public void getArticlesMaxReservePriceInOrder_ReservePriceOrder_ReturnsCorrectList(){
    List<Article> articles = articleManager.getArticlesMaxReservePriceInOrder(100.0, ArticleOrder.RESERVE_PRICE);
    Assert.assertEquals(articles.get(0).getReservePrice(), 100.0);
    Assert.assertEquals(articles.get(2).getReservePrice(), 58.0);
  }

  @Ignore
  //Test
  public void getArticlesMaxReservePriceInOrder_HammerPrice_ReturnsCorrectList(){
    List<Article> articles = articleManager.getArticlesMaxReservePriceInOrder(100.0, ArticleOrder.HAMMER_PRICE);
    Assert.assertEquals(articles.get(0).getHammerPrice(), 900.2);
    Assert.assertEquals(articles.get(2).getHammerPrice(), 110.0);
  }

  @Ignore
  //Test
  public void getArticlesMaxReservePriceInOrder_AuctionStartDate_ReturnsCorrectList(){
    List<Article> articles = articleManager.getArticlesMaxReservePriceInOrder(100.0, ArticleOrder.AUCTION_START_DATE);
    Assert.assertEquals(articles.get(0).getAuctionStartDate(), LocalDate.of(2022, 9,26));
    Assert.assertEquals(articles.get(2).getAuctionStartDate(), LocalDate.of(2019,4,26));
  }

  @Ignore
  //Test
  public void getArticlesMaxReservePriceInOrder_MaxReservePriceZeroOrNull_ReturnsFullOrderedList(){
    List<Article> articles_1 = articleManager.getArticlesMaxReservePriceInOrder(0.0, ArticleOrder.NAME);
    List<Article> articles_2 = articleManager.getArticlesMaxReservePriceInOrder(null, ArticleOrder.NAME);
    Assert.assertEquals(articles_1.get(0).getName(), "AName");
    Assert.assertEquals(articles_2.get(0).getName(), "AName");
  }

  @Ignore
  @Test
  public void insert_correctArticle_ReturnsTrue(){
    Article article = new Article();
    Long id = article.getId();
    article.setName("name");
    article.setAuctionStartDate( LocalDate.of(2023,01,01));
    article.setReservePrice(50.0);
    article.setDescription("description");

    Assert.assertTrue(articleManager.insert(article));
    articleManager.delete(article);
  }

  @Ignore
  //Test
  public void insert_null_ReturnsFalse(){
    Assert.assertFalse(articleManager.insert(null));
  }

  @Ignore
  //Test
  public void insert_alreadyExistingArticle_ReturnsFalse(){
    Assert.assertFalse(articleManager.insert(null));
  }

  @Ignore
  //Test
  public void update_correctArticle_UpdatesArticleAndReturnsTrue(){
    Article article = articleManager.getArticleById((long)1);
    String oldName = article.getName();
    article.setName("theNewName");

    Assert.assertTrue(articleManager.update(article));

    Assert.assertEquals(articleManager.getArticleById((long)1).getName(), "theNewName");
    Assert.assertNotEquals(articleManager.getArticleById((long)1).getName(), oldName);

    article.setName(oldName);
    articleManager.update(article);
    Assert.assertEquals(articleManager.getArticleById((long)1).getName(), oldName);
  }

  @Ignore
  //Test
  public void update_null_ReturnsFalse(){
    Assert.assertFalse(articleManager.update(null));
  }

  @Ignore
  //Test
  public void update_ArticleDoesNotExist_ReturnsFalse(){
    Article article = new Article();
    Assert.assertFalse(articleManager.update(article));
  }

  @Ignore
  //Test
  public void Z_delete_validArticle_ReturnsTrueAndDeletesArticle(){
    Article article = new Article((long)1,"DName", "abc", 100.0, 110.0, LocalDate.of(2019,04,26));
    Assert.assertTrue(articleManager.delete(article));
    articleManager.insert(article);
  }

  @Ignore
  //Test
  public void delete_ArticleDoesNotExist_ReturnsFalse(){
    Article article = new Article();
    Assert.assertFalse(articleManager.delete(article));
  }



  }
