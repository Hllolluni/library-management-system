import {Component, NgZone} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from '@angular/common/http'
import {UserInfo} from "../models/user-info";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {

  constructor(private fb: FormBuilder, private http: HttpClient, private snackBar: MatSnackBar, private zone: NgZone, private router: Router) {
  }

  paymentForm: FormGroup = this.fb.group({
    cardNumber: ['', [Validators.required]],
    name: ['', [Validators.required]],
    month: ['', [Validators.required]],
    year: ['', [Validators.required]],
    cvc: ['', [Validators.required]],
  })

  chargeCreditCard() {
    const config = new MatSnackBarConfig();
    config.verticalPosition = 'bottom';
    config.horizontalPosition = 'center';
    config.duration = 3000;

    (<any>window).Stripe.card.createToken({
      number: this.paymentForm.controls['cardNumber'].value,
      exp_month: Number(this.paymentForm.controls['month'].value),
      exp_year: this.paymentForm.controls['year'].value,
      cvc: this.paymentForm.controls['cvc'].value
    }, (status: number, response: any) => {
      if (status === 200) {
        let token = response.id;
        this.chargeCard(token, config);
      } else {
        this.zone.run(() => {
          this.snackBar.open("Failed to do the payment, please try again!", 'Close', config);
        });
        console.log(response.error.message);
      }
    });
  }

  chargeCard(token: string, config: any) {
    const headers = new HttpHeaders({'token': token, 'amount': 15});
    const body: UserInfo = new UserInfo(parseInt(localStorage.getItem('id') ?? ''), localStorage.getItem('firstName') ?? '', localStorage.getItem('email') ?? '');

    this.http.post('http://localhost:8083/api/payment/pay', body, {headers: headers})
      .subscribe(resp => {
        console.log(resp);
        this.zone.run(() => {
          this.snackBar.open("Payment done successfully!", 'Close', config);
        });
        this.router.navigate(['aboutus']);
      }, error => {
        console.log("It is a problem, should be tried again later!");
        this.zone.run(() => {
          this.snackBar.open("Failed to do the payment, please try again!", 'Close', config);
        });
      });
  }
}
