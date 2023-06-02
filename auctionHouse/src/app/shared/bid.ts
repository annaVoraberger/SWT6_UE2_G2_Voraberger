export class Bid {
  constructor(
    public id?:number,
    public bid?:number,
    public date?: number,
    public articleId?: number,
    public customerId?: number
  ){}
}
