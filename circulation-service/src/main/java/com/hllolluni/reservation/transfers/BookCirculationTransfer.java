package com.hllolluni.reservation.transfers;

import com.hllolluni.reservation.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookCirculationTransfer {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String bookIsbn;
    private String bookTitle;
    private LocalDateTime startingDate;
    private LocalDateTime returnDate;
    private ReservationStatus status;
}
