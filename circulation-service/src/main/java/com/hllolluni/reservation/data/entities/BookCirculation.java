package com.hllolluni.reservation.data.entities;

import com.hllolluni.reservation.enums.ReservationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookCirculation {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String bookIsbn;
    private String bookTitle;
    private LocalDateTime startingDate;
    private LocalDateTime returnDate;

    @Enumerated
    private ReservationStatus status;
}