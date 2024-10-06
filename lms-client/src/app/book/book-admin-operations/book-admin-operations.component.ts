import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import {BookService} from "../services/book.service";
import {MatSnackBar} from "@angular/material/snack-bar";
@Component({
  selector: 'app-book-admin-operations',
  templateUrl: './book-admin-operations.component.html',
  styleUrls: ['./book-admin-operations.component.css']
})
export class BookAdminOperationsComponent implements OnInit{
  categories: string[] = []
  bookForm: FormGroup;
  deleteForm: FormGroup;
  selectedFile: File | null = null;
  register: boolean = true;
  auths: string[] = []

  constructor(private fb: FormBuilder, private http: HttpClient, private bookService: BookService, private snackBar: MatSnackBar) {
    this.bookForm = this.fb.group({
      title: ['',  [Validators.required]],
      authors: this.fb.array([this.fb.group({name: ''})]),
      isbn: ['',  [Validators.required]],
      description: ['', [Validators.required]],
      copiesNumber: ['', [Validators.required]],
      daysToBeReturn: ['', [Validators.required]],
      pages: ['', [Validators.required]],
      category: ['', [Validators.required]]
    });

    this.deleteForm = this.fb.group({
      title: ['', [Validators.required]],
      isbn: ['', [Validators.required]],
    });
  }

  ngOnInit() {
     this.bookService.getCategories().subscribe(data => {
       this.categories = data;
       console.log(this.categories)
     });
  }

  onFileSelect(event: any): void {
    if (event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }

  get authors(): FormArray {
    return this.bookForm.get('authors') as FormArray;
  }

  addAuthor(): void {
    this.authors.push(this.fb.group({name: ''}));
  }

  removeAuthor(index: number): void {
    this.authors.removeAt(index);
  }

  replacer(key: any, value: any){
    if (key == "authors"){
      console.log(this.auths)
      return this.auths;
    }
    return value
  }

  onSubmit(): void {
    this.auths = this.bookForm.controls['authors'].value.map((val: any) => val['name']);
    const formData = new FormData();
    formData.append('image', this.selectedFile!);
    formData.append('request', JSON.stringify(this.bookForm.getRawValue() , (key, value) => this.replacer(key, value)));

    this.http.post('http://localhost:8083/api/books', formData).subscribe(response => {
      console.log('Upload successful', response);
      this.snackBar.open('Book saved successfully!', 'Close', {
        duration: 3000,
      });

      this.bookForm.reset();
      this.authors.clear();
      this.authors.push(this.fb.group({name: ''}));

      this.selectedFile = null;
    }, error => {
      console.log("It is a problem saving the book.", error)
      this.snackBar.open("Failed to save the book. Please try again!", 'Close',{
        duration: 3000,
      })
    });
  }

  delete() {
    this.register = !this.register;
    this.bookForm.reset();
  }

  onDelete() {
    const book = {title: this.deleteForm.controls['title'].value, isbn: this.deleteForm.controls['isbn'].value};
    this.bookService.deleteBook(book).subscribe(() => {
      this.snackBar.open('Book deleted successfully!', 'Close', {
        duration: 3000,
      });
      this.deleteForm.reset();
    }, error => {
      console.log("It is a problem deleting the book.", error)
      this.snackBar.open("Failed to delete the book. Please try again!", 'Close', {
        duration: 3000,
      })
    });
  }
}
