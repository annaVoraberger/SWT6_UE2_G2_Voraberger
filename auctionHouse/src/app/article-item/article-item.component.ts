import { Component, Input, OnInit } from '@angular/core';
import { AuctionService } from '../shared/auction.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Article } from '../shared/article';
import { Bid } from '../shared/bid';

@Component({
  selector: 'a.auct-article-item',
  templateUrl: './article-item.component.html',
  styleUrls: []
})
export class ArticleItemComponent implements OnInit {

  @Input() article: Article = new Article();
  myForm!: FormGroup;
  amount = 0;

  constructor(private auctionService: AuctionService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      amount: this.article.highestBid
    });
  }

  getImageLink(){
    return `../../../assets/images/${this.article.name}.jpeg`
  }

  placeBid(){
    this.amount = this.myForm.value.amount;
    if (this.amount > (0 && this.article.highestBid))
      this.auctionService.placeBid(
        new Bid(123,this.amount, Date.parse("1.1.2023")), this.article.id || 0, 1);
  }

}




