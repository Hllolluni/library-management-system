import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {PaymentService} from "../../book/services/payment.service";
import {take, map} from "rxjs";
import {BorrowComponent} from "../../book/borrow/borrow.component";
import {AuthService} from "../../services/auth.service";

export const paymentGuard: CanActivateFn = (route, state) => {
  const paymentService = inject(PaymentService);
  const authService = inject(AuthService);
  const router = inject(Router);

  return paymentService.checkPayment().pipe(
    take(1),
    map(data => {
      if (data != false || authService.isAdmin()) {
        console.log("Inside payment guard")
        router.navigate(['/aboutus']);
        return false;
      }
      return true;
    })
  );
}
