import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./authentication/login/login.component";
import {RegisterComponent} from "./authentication/register/register.component";
import {BookDetailComponent} from "./book/book-detail/book-detail.component";
import {BooksComponent} from "./book/books/books.component";
import {PaymentComponent} from "./payment/payment/payment.component";
import {BookAdminOperationsComponent} from "./book/book-admin-operations/book-admin-operations.component";
import {ReservationsComponent} from "./reservation/reservations/reservations.component";
import {authGuard} from "./guards/authGuards/auth.guard";
import {guestGuard} from "./guards/guestGuard/guest.guard";
import {AboutusComponent} from "./aboutus/aboutus/aboutus.component";
import {adminGuard} from "./guards/adminGuards/admin.guard";
import {BorrowComponent} from "./book/borrow/borrow.component";
import {paymentGuard} from "./guards/paymentGuards/payment.guard";
import {userGuard} from "./guards/userGuards/user.guard";

const routes: Routes = [
  {path: '', component: RegisterComponent, canActivate: [guestGuard]},
  {path: 'register', component: RegisterComponent, canActivate: [guestGuard]},
  {path: 'login', component: LoginComponent, canActivate: [guestGuard]},
  {path: 'book', component: BooksComponent, canActivate: [authGuard]},
  {path: 'book-detail', component: BookDetailComponent},
  {path: 'book-admin', component: BookAdminOperationsComponent, canActivate: [authGuard, adminGuard]},
  {path: 'payment', component: PaymentComponent, canActivate: [authGuard, paymentGuard]},
  {path: 'reservations', component: ReservationsComponent, canActivate: [authGuard, adminGuard]},
  {path: 'borrow', component: BorrowComponent, canActivate: [authGuard]},
  {path: 'aboutus', component: AboutusComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
