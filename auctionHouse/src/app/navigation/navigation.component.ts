import { Component, OnInit } from '@angular/core';
import { AuctionService } from '../shared/auction.service';

@Component({
  selector: 'auct-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor(private auctionService: AuctionService) { }

  public isLoggedIn:boolean = false;

  ngOnInit(): void {
  }

  logout(){
    this.isLoggedIn = false;
  }

}


