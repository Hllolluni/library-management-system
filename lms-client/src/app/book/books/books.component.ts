import {Component, OnInit} from '@angular/core';
import {BookService} from "../services/book.service";
import {BookModel} from "../models/book-model";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {
  books: BookModel[] = [];
  category!: string;

  constructor(private bookService: BookService, private router: Router, private aRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.router.onSameUrlNavigation = "reload"

    this.aRoute.queryParams.subscribe(params => {
        this.category = params['categoryName'];
      }
    );
    this.bookService.getBooksByCategory(this.category).subscribe(data => {
      this.books = data;
      this.books.forEach(book => book.image = this.createImageFromBlob(book))
    });
  }

  getTitle(book: BookModel): string {
    if (book.title.length >= 27) {
      return book.title.slice(0, 24) + "...";
    }
    return book.title;
  }

  navigate(bookId: number) {
    let book = this.books.find(b => b.id == bookId);
    console.log(book)
    this.router.navigate(['/book-detail'], {state: {book}});
  }

  createImageFromBlob(book: any) {
    let imageToShow = 'data:image/jpeg;base64,' + book.image;
    return imageToShow;
  }
}
