export class Article {
  public id?: number;
  public name?: string;
  public description?: string;
  public reservePrice?: number;
  public hammerPrice?: number;
  public auctionStartDate?: Date;
  public auctionEndDate?: Date;
  public highestBid?: number;
  public sellerName?: string;
  public status?: string;
}
