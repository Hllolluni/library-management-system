import {Component, Inject, OnInit} from '@angular/core';
import {BookService} from "../services/book.service";
import {Router} from "@angular/router";
import {PaymentService} from "../services/payment.service";
import {BorrowService} from "../services/borrow.service";
import {PaymentCheck} from "../models/payment-check";
import {HttpClient} from "@angular/common/http";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {BookModel} from "../models/book-model";

@Component({
  selector: 'app-borrow',
  templateUrl: './borrow.component.html',
  styleUrls: ['./borrow.component.css']
})
export class BorrowComponent{
  protected borrowed: boolean = false;
  protected question: string = 'Do you want to borrow this book?';
  private id!: number;

  constructor(private borrowService: BorrowService, private bookService: BookService, @Inject(MAT_DIALOG_DATA) public data: any) {
    this.id = data.id;
  }

  borrowReq() {
    let bookId: number = this.id;
    let userId: number = parseInt(localStorage.getItem('id')?? '');
    let email: string = localStorage.getItem('email')??'';
    this.borrowService.borrowRequest(bookId, userId, email).subscribe(data => {
      console.log(data)
      if (data == "created"){
        this.borrowed = true;
        this.question = 'Congrats, you have borrowed the book!';
      }
    },error => {
      this.borrowed = true;
      this.question = 'You can not reserve this book now. Please, try again later!';
    });
  }

}
