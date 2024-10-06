import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Reservation} from "../reservation";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(private http: HttpClient) { }

  getReservations() {
    return this.http.get<Reservation[]>("http://localhost:8082/api/borrow/reservations");
  }

  setCompleted(clickedRows: Set<Reservation>) {
    console.log(clickedRows)
    return this.http.post('http://localhost:8082/api/borrow/complete', Array.from(clickedRows)).subscribe(
      data => {
        console.log(data)
      }
    )
  }
}
