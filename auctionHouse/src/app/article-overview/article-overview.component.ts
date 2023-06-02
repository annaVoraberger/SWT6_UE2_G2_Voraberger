import { Component, EventEmitter, OnInit, Output } from '@angular/core';


import { Article } from '../shared/article';
import { AuctionService } from '../shared/auction.service';
import { Router } from '@angular/router';

@Component({
  selector: 'auct-article-overview',
  templateUrl: './article-overview.component.html',
  styles: []
})
export class ArticleOverviewComponent implements OnInit {

  @Output() showDetailsEvent = new EventEmitter<Article>();
  articles: Article[] = [];
  


  constructor(private auctionService: AuctionService,
              private router: Router) { }

  ngOnDestroy(): void {

  }

  showDetails(article: Article) {
    this.showDetailsEvent.emit(article);
  }

  photoSelected(article: Article) {
    this.router.navigate(['../photos', article.id]);
  }


  ngOnInit(): void {
    this.auctionService.getAllArticles().subscribe(res => this.articles = res);
  }



}
