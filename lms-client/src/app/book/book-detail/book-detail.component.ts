import {Component, OnInit} from '@angular/core';
import {BookService} from "../services/book.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {BorrowComponent} from "../borrow/borrow.component";
import {BookModel} from "../models/book-model";

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {
  book!: BookModel;

  constructor(private bookService: BookService, private router: Router, private dialogRef: MatDialog) {
    const navigation = this.router.getCurrentNavigation();

    if (navigation?.extras.state){
      this.book = navigation.extras.state['book'];
      console.log(this.book.id)
    }
  }

  borrow() {
    this.dialogRef.open(BorrowComponent, {
        data: {
          id: this.book.id
        },
        width: '300px',
        height: '150px',
      }
    );
  }

  ngOnInit() {

  }
}
