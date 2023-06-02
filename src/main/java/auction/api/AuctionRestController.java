package auction.api;

import auction.domain.Article;
import auction.domain.ArticleStatus;
import auction.domain.Bid;
import auction.domain.Customer;
import auction.dtos.ArticleDto;
import auction.dtos.BidPostDto;
import auction.dtos.CustomerDto;
import auction.logic.ArticleNotFoundException;
import auction.logic.AuctionLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NoArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/auction", produces = MediaType.APPLICATION_JSON_VALUE)
@NoArgsConstructor
public class AuctionRestController {
  @Autowired
  AuctionLogic logic;
  @Autowired
  ModelMapper mapper;

  @GetMapping(value = "/articles")
  public List<ArticleDto> getAllArticles(){
    var list = this.logic.getAllArticles();

    var result = list.stream()
            .map((article -> {
              ArticleDto a = mapper.map(article, ArticleDto.class);
              a.setHighestBid(logic.getHighestBidOnArticle(article));
              a.setSellerName(article.getSeller().getLastname());
              return a;}))
            .toList();
    return result;
  }

  @GetMapping(value = "/articles/running")
  public List<ArticleDto> getAllRunningArticles(){
    var list = this.logic.getArticlesByState(ArticleStatus.AUCTION_RUNNING);
    var result = list.stream()
            .map((article -> {
              ArticleDto a = mapper.map(article, ArticleDto.class);
              a.setHighestBid(logic.getHighestBidOnArticle(article));
              a.setSellerName(article.getSeller().getLastname());
              return a;}))
            .toList();
    return result;
  }

  @GetMapping(value = "/articles/{id}")
  @Operation(summary="Get article from database")
  @ApiResponse(responseCode = "404 if employee not found")
  public ArticleDto getArticleById(@PathVariable Long id) throws ArticleNotFoundException {
    Article article = logic.getArticleById(id);
    if (article == null) throw new ArticleNotFoundException();
    ArticleDto a = mapper.map(article, ArticleDto.class);
    a.setHighestBid(logic.getHighestBidOnArticle(article));
    a.setSellerName(article.getSeller().getLastname());
    return a;
  }

  @PostMapping(value = "/articles")
  public ResponseEntity addArticle(@RequestBody ArticleDto articleDto){
    Article article = mapper.map(articleDto, Article.class);
    if (logic.addArticle(article)){
      articleDto.setId(article.getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(articleDto);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed adding article");
  }

  @PostMapping(value="/articles/{id}")
  @Operation(summary="Get article from database")
  public ResponseEntity placeBidOnArticle(@PathVariable Long id, @RequestBody BidPostDto bidDto, @RequestParam Long customerId) {
    Bid bid = mapper.map(bidDto, Bid.class);
    if (logic.addBid(bid, customerId, id)) {
      return ResponseEntity.ok("Bid successfully placed.");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
  }

  @PostMapping(value = "/customers")
  public ResponseEntity addCustomer(@RequestBody CustomerDto customerDto){
    Customer customer = mapper.map(customerDto, Customer.class);
    if (logic.addCustomer(customer)){
      customerDto.setId(customer.getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed adding customer");
  }

  @PostMapping(value="/login")
  public ResponseEntity authenticate(@RequestBody Customer customer){
    if(logic.authenticateCustomer(customer.getEMail())){
      return ResponseEntity.ok("logged in successfully");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("wrong credentials");
  }
}

