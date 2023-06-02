import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable, catchError, map, of } from 'rxjs';
import { Article } from './article';
import { Bid } from './bid';
import { Customer } from './customer';



@Injectable({
  providedIn: 'root'
})
export class AuctionService {

  constructor(private http: HttpClient) { }

  private errorHandler(error: Error | any): Observable<any> {
    console.log(error);
    return of(error);
  } 

  getAllArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(`${environment.server}/articles/running`) //only currently running ones in UI
      .pipe(map<any, Article[]>(res => res), catchError(this.errorHandler));
  }

  getArticleById(id:number): Observable<Article> {
    return this.http.get<Article>(`${environment.server}/articles/${id}`)
      .pipe(map<any, Article>(res => res),
      catchError(this.errorHandler));
  }

  placeBid(bid: Bid, articleId: number, customerId: number): Observable<any>{
    return this.http.post<Bid>(`${environment.server}/articles/${articleId}`, bid)
      .pipe(catchError(this.errorHandler));
  }

  authenticate(email: string): Observable<any> {
    var customer = new Customer();
    customer.email=email;
    return this.http.post<string>(`${environment.server}/login`, customer)
    .pipe(catchError(this.errorHandler));
  }


}


  