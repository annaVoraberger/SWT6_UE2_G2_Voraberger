import { Component, OnInit } from '@angular/core';
import { Customer } from '../shared/customer';
import { FormBuilder, FormGroup, Form } from '@angular/forms';
import { AuctionService } from '../shared/auction.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'auct-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  myForm!: FormGroup;
  customer = new Customer();
  errors: { [key: string]: string } = {};

  constructor(private fb: FormBuilder,
    private auctionService: AuctionService,
    private route: ActivatedRoute,
    private router: Router) 
    { }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    // we are using a FormBuilder to fill the Form-Model
    this.myForm = this.fb.group({
      email: [this.customer.email],
      password: this.customer.password,
    });
  }

  submitForm() {
    const customer: Customer = this.myForm.value;
      this.auctionService.authenticate(customer.email || "").subscribe()
      this.router.navigateByUrl("articles");
    }
  }
