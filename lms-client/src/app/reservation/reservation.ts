export class Reservation {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  bookIsbn: string;
  bookTitle: string;
  startingDate: string;
  returnDate: string;
  status: number;

  constructor(id: string, firstName: string, lastName: string, email: string, bookIsbn: string, bookTitle: string, startingDate: string, returnDate: string, status: number) {
    this.id = id;
    this.bookTitle = bookTitle;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.bookIsbn = bookIsbn;
    this.bookTitle = bookTitle;
    this.startingDate = startingDate;
    this.returnDate = returnDate;
    this.status = status;
  }
}
