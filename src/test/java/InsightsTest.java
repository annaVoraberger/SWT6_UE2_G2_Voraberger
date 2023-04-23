import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import auction.util.TransactionsUtil;


public class InsightsTest {


  @BeforeClass
  public static void init(){
    TransactionsUtil.getEntityManagerFactory();
  }

  @AfterClass
  public static void cleanup(){
    TransactionsUtil.closeEntityManagerFactory();
  }


  @Test
  public static void findArticlesByDescription_getsJustDescription_returnsCorrectList(){

  }

  @Test
  public static void findArticlesByDescription_getsNonexistantDescription_returnsNull(){

  }

  @Test
  public static void findArticlesByDescription_getsSearchPhraseAndMaxReservePrice_returnsCorrectList(){

  }

  @Test
  public static void findArticlesByDescription_getsNonexistantSearchPhraseAndMaxReservePrice_returnsNull(){

  }

  @Test
  public static void findArticlesByDescription_getsPhrasePriceAndOrder_TestingAllOrders_returnsCorrectLists(){

  }

  @Test
  public static void findArticlesByDescription_getsNonexistantPhrasePriceAndOrder_TestingAllOrders_returnsNull(){

  }

  @Test
  public static void getArticlePrice_AuctionRunning_ReturnsHighestBid(){

  }

  @Test
  public static void getArticlePrice_AuctionEnded_ReturnsHighestHammerPrice(){

  }

  @Test
  public static void getArticlePrice_AuctionOverArticleNotSold_ReturnsNull(){

  }

  @Test
  public static void getArticlePrice_ArticleDoesNotExist_ThrowsArticleNotFoundException(){

  }

  @Test
  public static void getTopSellers_CountValid_ReturnsCountNumberOfTopSellers(){

  }

  @Test
  public static void getTopSellers_CountInValid_ReturnsEmptyList(){

  }

  @Test
  public static void getTopArticles_CountValid_ReturnsCountNumberOfTopArticles(){

  }

  @Test
  public static void getTopArticles_CountInValid_ReturnsEmptyList(){

  }


}
