import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './authentication/login/login.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {ReactiveFormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {RegisterComponent} from './authentication/register/register.component';
import {MatSelectModule} from "@angular/material/select";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {BooksComponent} from './book/books/books.component';
import {BookDetailComponent} from './book/book-detail/book-detail.component';
import {BorrowComponent} from './book/borrow/borrow.component';
import {MatDialogModule} from "@angular/material/dialog";
import {PaymentComponent} from './payment/payment/payment.component';
import {BookAdminOperationsComponent} from './book/book-admin-operations/book-admin-operations.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatStepperModule} from "@angular/material/stepper";
import {MatTabsModule} from "@angular/material/tabs";
import {MatToolbarModule} from "@angular/material/toolbar";
import {NavbarComponent} from './navbar/navbar.component';
import {MatMenuModule} from "@angular/material/menu";
import {MatTreeModule} from "@angular/material/tree";
import {ReservationsComponent} from './reservation/reservations/reservations.component';
import {MatListModule} from "@angular/material/list";
import {MatTableModule} from "@angular/material/table";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {AuthService} from "./services/auth.service";
import { AboutusComponent } from './aboutus/aboutus/aboutus.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    BooksComponent,
    BookDetailComponent,
    BorrowComponent,
    PaymentComponent,
    BookAdminOperationsComponent,
    NavbarComponent,
    ReservationsComponent,
    AboutusComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    MatSelectModule,
    HttpClientModule,
    MatDialogModule,
    MatSnackBarModule,
    MatStepperModule,
    MatTabsModule,
    MatToolbarModule,
    MatMenuModule,
    MatTreeModule,
    MatListModule,
    MatTableModule,
    MatCheckboxModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
