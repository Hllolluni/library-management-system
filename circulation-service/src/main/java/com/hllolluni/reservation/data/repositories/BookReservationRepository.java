package com.hllolluni.reservation.data.repositories;

import com.hllolluni.reservation.data.entities.BookCirculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookReservationRepository extends JpaRepository<BookCirculation, Long> {

    @Query("select r from BookCirculation r where r.returnDate < :currentDate and r.status = 1")
    List<BookCirculation> findAllExpired(@Param("currentDate") LocalDateTime currentDate);

    Optional<BookCirculation> findBookCirculationByEmailAndBookTitle(String email, String isbn);

    @Query("select r from BookCirculation r where r.status = 0")
    List<BookCirculation> findAllUnCompleted();
}
