import {Component, OnInit} from '@angular/core';
import {BookService} from "../book/services/book.service";
import {Router} from "@angular/router";
import {Classification} from "../classification";
import {AuthService} from "../services/auth.service";
import {PaymentService} from "../book/services/payment.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  classifications: Classification[] = [];

  constructor(protected authService: AuthService, protected paymentService: PaymentService,private bookService: BookService, private router: Router) {
  }

  ngOnInit() {
    this.bookService.getClassifications().subscribe(data => {
      this.classifications = data;
    });
  }

  navigateTo(path: string, category: string) {
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigate([path], {queryParams: {categoryName: category}});
    });
  }



  logout() {
    this.authService.logout();
  }
}
