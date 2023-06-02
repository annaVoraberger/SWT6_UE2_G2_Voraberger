package auction.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Customer {

  @Id
  @GeneratedValue
  private Long id;

  private String firstname;

  private String lastname;

  private String eMail;

  @Embedded
  @AttributeOverride(name = "street", column = @Column(name = "payA_street"))
  @AttributeOverride(name = "houseNr", column = @Column(name = "payA_houseNr"))
  @AttributeOverride(name = "zipCode", column = @Column(name = "payA_zipCode"))
  @AttributeOverride(name = "town", column = @Column(name = "payA_town"))
  private Address paymentAddress;

  @Embedded
  @AttributeOverride(name = "street", column = @Column(name = "shipA_street"))
  @AttributeOverride(name = "houseNr", column = @Column(name = "shipA_houseNr"))
  @AttributeOverride(name = "zipCode", column = @Column(name = "shipA_zipCode"))
  @AttributeOverride(name = "town", column = @Column(name = "shipA_town"))
  private Address shippingAddress;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PaymentOption> paymentOptions = new HashSet<>();

  @OneToMany(mappedBy = "seller", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Article> soldArticles = new HashSet<>();

  @OneToMany(mappedBy = "buyer", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Article> boughtArticles = new HashSet<>();

  @OneToMany(mappedBy = "bidder", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Bid> bids = new HashSet<>();

  public Customer(String firstname, String lastname, String eMail) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.eMail = eMail;
  }


  public void addPaymentOption(PaymentOption paymentOption){
    if (paymentOption.getOwner() != null){
      //remove bid from other bidder
      paymentOption.getOwner().paymentOptions.remove(paymentOption);
    }
    paymentOptions.add(paymentOption);
    paymentOption.setOwner(this);
  }

  public void removePaymentOption(PaymentOption paymentOption){
    if (paymentOptions.remove(paymentOption)){
      paymentOption.setOwner(null);
    }
  }

  public void addSoldArticle(Article article){
    if (article.getSeller() != null){
      //remove bid from other bidder
      article.getSeller().soldArticles.remove(article);
    }
    soldArticles.add(article);
    article.setSeller(this);
  }

  public void removeSoldArticle(Article article){
    if (soldArticles.remove(article)){
      article.setSeller(null);
    }
  }

  public void addBoughtArticle(Article article){
    if (article.getBuyer() != null){
      //remove bid from other bidder
      article.getBuyer().boughtArticles.remove(article);
    }
    boughtArticles.add(article);
    article.setBuyer(this);
  }

  public void removeBoughtArticle(Article article){
    if (boughtArticles.remove(article)){
      article.setBuyer(null);
    }
  }


  public void addBid(Bid bid){
    if (bid.getBidder() != null){
      //remove bid from other bidder
      bid.getBidder().bids.remove(bid);
    }
    bids.add(bid);
    bid.setBidder(this);
  }

  public void removeBid(Bid bid){
    if (bids.remove(bid)){
      bid.setBidder(null);
    }
  }

}
