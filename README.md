# Übung 2 - Auktionsbackend
Anna Voraberger | S2010307040
Aufwand: 15:00-16:30

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

## PaymentOption
Es gibt zwei Zahlungsarten: Bankkonten und Kreditkarten.
- owner (Name des Kredikarten/Konto-Besitzers)
- bankAccountNumber (IBAN)
- bankIdentifier (BIC)
- creditCardNumber
- creditCardValidTo
- cardVerificationValue (CVV)
Hinweis: Überlegen Sie die Verwendung von "JPA Inheritance".

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

  @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
  private Set<Bid> bids = new HashSet<>();

  private ArticleStatus status;
}

```
Die Beziehung zu den Bids habe ich als ```OneToMany``` Beziehung umgesetzt, da es sich um eine 1:N Beziehung handelt.

### Cascade Type
Den Cascade Type habe ich auf ```REMOVE``` gesetzt, damit beim Löschen eines Artikels auch dessen Bids gelöscht werden. Diese werden dann nicht mehr gebraucht.
Ich habe mich gegen ```Cascade.PERSIST``` bzw. ```Cascade.MERGE``` entschieden: Es beeinflusst Bids nicht, wenn Name, Preis o.Ä. beim Artikel verändert werden. Ist ein Bid einmal abgesetzt, muss daran nichts verändert werden.

### Article Status
Den Article Status habe ich als ```enum``` Type angelegt.


## Bid
- bid (gebotener Preis)
- date
- bidder → Customer
- article → Article (auf den sich das Gebot bezieht; je Artikel können mehrere Gebote abgegeben werden)
 
