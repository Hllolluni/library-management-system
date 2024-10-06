import {Injectable} from '@angular/core';
import {BookModel} from "../models/book-model";
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {Classification} from "../../classification";

@Injectable({
  providedIn: 'root'
})
export class BookService {

  books: BookModel[] = []
  categories: string[] = []

  constructor(private http: HttpClient) {
  }

  public getBooks(): Observable<BookModel[]> {
    return this.http.get<BookModel[]>("http://localhost:8083/api/books").pipe(tap((data) => this.books = data));
  }

  public getCategories(): Observable<string[]> {
    return this.http.get<string[]>("http://localhost:8083/api/category/categories_to_string").pipe(tap((data) => this.categories = data));
  }

  public deleteBook(book: any): Observable<void> {
    return this.http.request<void>('delete', "http://localhost:8083/api/books", {body: book});
  }

  getClassifications() {
    return this.http.get<Classification[]>("http://localhost:8083/api/classification/classifications");
  }

  getBooksByCategory(category: string) {
    return this.http.get<BookModel[]>("http://localhost:8083/api/books/category?categoryName="+category);
  }
}
