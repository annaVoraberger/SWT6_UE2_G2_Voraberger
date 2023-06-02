import { Component, EventEmitter, OnInit, Output } from '@angular/core';


import { Article } from '../shared/article';
import { AuctionService } from '../shared/auction.service';

@Component({
  selector: 'auct-article-overview',
  templateUrl: './article-overview.component.html',
  styleUrls: ['./article-overview.component.css']
})
export class ArticleOverviewComponent implements OnInit {

  @Output() showDetailsEvent = new EventEmitter<Article>();
  articles: Article[] = [];


  constructor(private auctionService: AuctionService,
              /*private router: Router*/) { }

  ngOnDestroy(): void {

  }

  showDetails(article: Article) {
    this.showDetailsEvent.emit(article);
  }

  photoSelected(article: Article) {
    this.router.navigate(['../photos', article.id]);
  }


  ngOnInit(): void {
    this.articleService.getAll().subscribe(res => this.articles = res);
  }



}
