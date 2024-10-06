import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) {
  }

  public doPayment() {

  }

  checkPayment() {
    return this.http.get<boolean>("http://localhost:8083/api/payment/checkPayment?userId=" + localStorage.getItem('id')
      + "&email=" + localStorage.getItem('email'));
  }
}
