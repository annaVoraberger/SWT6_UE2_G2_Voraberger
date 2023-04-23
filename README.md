# Übung 2 - Auktionsbackend
Anna Voraberger | S2010307040
Aufwand: 15:00-16:30
17:45-18:45
09:00 - 10:30

# Domänenmodell | ER-Model
## ER Model

<img src="ER-Model.drawio.png"/>

## Customer
- firstname
- lastname
- eMail
- shippingAddress
- paymentAddress
- paymentMethods → PaymentOption (ein Kunde kann mehrere Zahlungsmöglichkeiten hinterlegen) sold/bought Articles → Article

```java
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
  private Address paymentAddress;

  @Embedded
  private Address shippingAddress;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PaymentOption> paymentOptions = new HashSet<>();

  @OneToMany(mappedBy = "seller", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Article> soldArticles = new HashSet<>();

  @OneToMany(mappedBy = "buyer", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Article> boughtArticles = new HashSet<>();

  @OneToMany(mappedBy = "bidder", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Bid> bids = new HashSet<>();
}
```
### Relationships
Der Customer hat sowohl eine eingebettete payment als auch shipping Address.
Diese Klasse ist auch die owner-Klasse für die Beziehungen zu den PaymentOptions, den bids, boughtArticles und den soldArticles, da es sich jeweils um 1:N Beziehungen handelt.

Die Cascadetypes wurden so gewählt dass Articles nicht notwendigerweise abhängig vom Customer existieren, da immer 2 dranhängen. Gibt es den seller oder buyer nicht mehr, soll der Article nicht gelöscht werden - daher kein ```Cascade.REMOVE``` und kein Orphanremoval.

Bids sollen für Auswertungszwecke ebenfalls noch zur Verfügung stehen, falls es den Customer nicht mehr gibt, daher ebenfalls kein ```REMOVE```.

Zahlungsmethoden werden ohne Customer nicht mehr gebraucht, daher ```Cascade.ALL``` und ```orphanRemoval = true```.



## PaymentOption
Es gibt zwei Zahlungsarten: Bankkonten und Kreditkarten.
- owner (Name des Kredikarten/Konto-Besitzers)
- bankAccountNumber (IBAN)
- bankIdentifier (BIC)
- creditCardNumber
- creditCardValidTo
- cardVerificationValue (CVV)

### Inheritance
```PaymentOption``` als Klasse selbst beinhaltet die Id, den Namen auf der Zahlungsmethode und den Customer, der als Besitzer hinterlegt ist. Der Name wird hier nochmal gespeichert, da der Customer Name und der auf der Karte angegebene Name sich unterscheiden können. Die Klassen Bankaccount und Creditcard leiten davon ab und beinhalten die eigentlichen Zahlungsinformationen zum jeweiligen Zahlungsmittel.

Daher wird auch die Inheritance strategy ```JOINED``` verwendet, sodass jede Zahlungsmethode eine eigene Tabelle erhält, in der die Id als Foreign Key mitgespeichert wird. Da auf die Zahlungsmethode voraussichtlich immer über die Parentklasse zugegriffen wird, muss nicht jedesmal eine UNION gemacht werden, wie das bei ```TABLE_PER_CLASS```der Fall wäre. Die ```SINGLE_TABLE```Strategie würde sehr viele Einträge mit NULL mit sich bringen.

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class PaymentOption {

  @Id
  @GeneratedValue
  private Long id;

  private String ownerName;

  @ManyToOne
  private Customer owner;
}
```

```java
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Creditcard extends PaymentOption {

  private Long number;

  private Date validUntil;

  private int cvv;
}
```

```java
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bankaccount extends PaymentOption{

  private String owner;

  private Long IBAN;

  private String BIC;
}
```

Die Zahlungsmethoden eines Customers werden als Liste beim Customer geführt und als OneToMany-ManyToOne Beziehung modelliert.

## Article
- name
- description
- reservePrice (Ausrufungspreis, untere Preisgrenze)
- hammerPrice (finaler Preis, falls der Artikel erfolgreich versteigert wurde) auctionStartDate
- auctionEndDate
- seller → Customer
- buyer → Customer (falls erfolgreich versteigert)
- status
  - LISTED (erfasst, aber Auktion nicht gestartet)
  - AUCTION_RUNNING
  - SOLD (Auktion beendet, Artikel wurde versteigert)
  - NOT_SOLD (Auktion beendet, Artikel wurde nicht versteigert)

```java
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Article {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private String description;

  private Double reservePrice;

  private Double hammerPrice;

  private Date auctionStartDate;

  private Date auctionEndDate;

  @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private Set<Bid> bids = new HashSet<>();

  private ArticleStatus status;

  @ManyToOne
  private Customer buyer;

  @ManyToOne
  private Customer seller;
}

```
### Relationships
Die Beziehung zu den Bids habe ich als ```OneToMany``` Beziehung umgesetzt, da es sich um eine 1:N Beziehung handelt.

Den Cascade Type habe ich auf ```REMOVE``` gesetzt, damit beim Löschen eines Artikels auch dessen Bids gelöscht werden. Diese werden dann nicht mehr gebraucht.
Ich habe mich gegen ```Cascade.PERSIST``` bzw. ```Cascade.MERGE``` entschieden: Es beeinflusst Bids nicht, wenn Name, Preis o.Ä. beim Artikel verändert werden. Ist ein Bid einmal abgesetzt, muss daran nichts verändert werden.

### Article Status
Den Article Status habe ich als ```enum``` Type angelegt.


## Bid
- bid (gebotener Preis)
- date
- bidder → Customer
- article → Article (auf den sich das Gebot bezieht; je Artikel können mehrere Gebote abgegeben werden)

```java
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bid {

  @Id
  @GeneratedValue
  private Long id;

  private Double bid;

  private Date date;

  @ManyToOne
  private Article article;

  @ManyToOne
  private Customer bidder;
}
```

In der Bid sind nur noch die "non-owning" seiten der Beziehungen zu article und bidder zusätzlich zu den Bidding-Infos wie Preis und Datum angelegt.


 
