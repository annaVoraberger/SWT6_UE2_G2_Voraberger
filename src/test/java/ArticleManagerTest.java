import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import auction.domain.Article;
import auction.logic.ArticleOrder;
import auction.managers.ArticleManager;
import auction.util.TransactionsUtil;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ArticleManagerTest {

  static ArticleManager articleManager = new ArticleManager();

  @BeforeClass
  public static void init(){
    TransactionsUtil.getEntityManagerFactory();
    /*articleManager.insert(new Article(
            (long)1, "DName", "abc", 100.0, 110.0,LocalDate.of(2019,04,26)));
    articleManager.insert(new Article(
            (long)2, "EName", "def", 240.1, 840.5,LocalDate.of(2000,01,01)));
    articleManager.insert(new Article(
            (long)3, "CName", "ghi", 105.0, 209.2,LocalDate.of(2023,01,01)));
    articleManager.insert(new Article(
            (long)4, "ZName", "jkl",  58.0, 305.0,LocalDate.of(2022,05,15)));
    articleManager.insert(new Article(
            (long)5, "AName", "mno", 62.78, 900.2,LocalDate.of(2022,9,26)));
    articleManager.insert(new Article(
            (long)5, "BName", "pqr", 580.2, 600.2,LocalDate.of(2008,11,30)));
*/
  }


  @AfterClass
  public static void cleanup(){
    TransactionsUtil.closeEntityManagerFactory();
  }

  @Test
  public void getArticleById_returnsCorrectArticle(){

    Article article = articleManager.getArticleById((long)1);
    Assert.assertEquals(article.getId(), (long)1);
  }

  @Test
  public void getArticles_returnsAllArticles(){
    List<Article> articles = articleManager.getArticles();
    Assert.assertEquals(articles.size(), 6); //TODO

  }
  /*
  // Successful with empty DB
  @Test
  public void getArticles_EmptyDataBase_returnsEmptyList(){
    List<Article> articles = articleManager.getArticles();
    Assert.assertEquals(articles.size(), 0); //TODO
  }*/

  @Test
  public void getArticlesMaxReservePrice_returnsCorrectArticles() {
    List<Article> articles = articleManager.getArticlesMaxReservePrice(100.0);
    Assert.assertEquals(articles.size(), 3); //TODO
    Assert.assertTrue(articles.get(0).getReservePrice()<=100.0);
  }

  @Test
  public void getArticlesMaxReservePrice_MaxReservePriceZero_ReturnsEmptyList(){
    List<Article> articles = articleManager.getArticlesMaxReservePrice(0.0);
    Assert.assertEquals(articles.size(), 0);
  }

  @Test
  public void getArticlesMaxReservePrice_MaxReservePriceNull_ReturnsEmptyList(){
    List<Article> articles = articleManager.getArticlesMaxReservePrice(null);
    Assert.assertEquals(articles.size(), 0);
  }

  @Test
  public void getArticlesMaxReservePriceInOrder_NameOrder_ReturnsCorrectList(){
    List<Article> articles = articleManager.getArticlesMaxReservePriceInOrder(100.0, ArticleOrder.NAME);
    Assert.assertEquals(articles.get(0).getName(), "AName");
    Assert.assertEquals(articles.get(2).getName(), "ZName");
  }

  @Test
  public void getArticlesMaxReservePriceInOrder_ReservePriceOrder_ReturnsCorrectList(){
    List<Article> articles = articleManager.getArticlesMaxReservePriceInOrder(100.0, ArticleOrder.RESERVE_PRICE);
    Assert.assertEquals(articles.get(0).getReservePrice(), 100.0);
    Assert.assertEquals(articles.get(2).getReservePrice(), 58.0);
  }

  @Test
  public void getArticlesMaxReservePriceInOrder_HammerPrice_ReturnsCorrectList(){
    List<Article> articles = articleManager.getArticlesMaxReservePriceInOrder(100.0, ArticleOrder.HAMMER_PRICE);
    Assert.assertEquals(articles.get(0).getHammerPrice(), 900.2);
    Assert.assertEquals(articles.get(2).getHammerPrice(), 110.0);
  }

  @Test
  public void getArticlesMaxReservePriceInOrder_AuctionStartDate_ReturnsCorrectList(){
    List<Article> articles = articleManager.getArticlesMaxReservePriceInOrder(100.0, ArticleOrder.AUCTION_START_DATE);
    Assert.assertEquals(articles.get(0).getAuctionStartDate(), LocalDate.of(2022, 9,26));
    Assert.assertEquals(articles.get(2).getAuctionStartDate(), LocalDate.of(2019,4,26));
  }

  @Test
  public void getArticlesMaxReservePriceInOrder_MaxReservePriceZeroOrNull_ReturnsFullOrderedList(){
    List<Article> articles_1 = articleManager.getArticlesMaxReservePriceInOrder(0.0, ArticleOrder.NAME);
    List<Article> articles_2 = articleManager.getArticlesMaxReservePriceInOrder(null, ArticleOrder.NAME);
    Assert.assertEquals(articles_1.get(0).getName(), "AName");
    Assert.assertEquals(articles_2.get(0).getName(), "AName");
  }

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

  @Test
  public void insert_null_ReturnsFalse(){
    Assert.assertFalse(articleManager.insert(null));
  }

  @Test
  public void insert_alreadyExistingArticle_ReturnsFalse(){
    Assert.assertFalse(articleManager.insert(null));
  }

  @Test
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

  @Test
  public void update_null_ReturnsFalse(){
    Assert.assertFalse(articleManager.update(null));
  }

  @Test
  public void update_ArticleDoesNotExist_ReturnsFalse(){
    Article article = new Article();
    Assert.assertFalse(articleManager.update(article));
  }

  @Test
  public void delete_validArticle_ReturnsTrueAndDeletesArticle(){
    Article article = articleManager.getArticleById((long)1);
    Assert.assertTrue(articleManager.delete(article));
    articleManager.insert(article);
  }

  @Test
  public void delete_ArticleDoesNotExist_ReturnsFalse(){
    Article article = new Article();
    Assert.assertFalse(articleManager.delete(article));
  }



  }
