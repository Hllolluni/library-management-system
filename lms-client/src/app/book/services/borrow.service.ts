import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {BookModel} from "../models/book-model";
import {tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BorrowService {

  constructor(private http: HttpClient) {
  }

  public borrowRequest(bookId: any, userId: any, email: any) {
    const params = new HttpParams()
      .set('userId', userId)
      .set('email', email);
    return this.http.post("http://localhost:8083/api/borrow/" + bookId, null, { params });
  }
}
