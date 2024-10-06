import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Reservation} from "../reservation";
import {ReservationService} from "../services/reservation.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit{
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['Book Title', 'First Name', 'Last Name'];
  clickedRows = new Set<Reservation>();

  constructor(private reservationService: ReservationService) {
  }
  ngOnInit(): void {
    this.reservationService.getReservations().subscribe(data => {
      this.reservations = data;
    });
  }

  onRowClicked(row: Reservation){
    if (this.clickedRows.has(row)){
      this.clickedRows.delete(row);
      return
    }
    this.clickedRows.add(row)
  }

  setCompleted() {
    this.reservationService.setCompleted(this.clickedRows);
    this.clickedRows.clear();
    window.location.reload();
  }
}
