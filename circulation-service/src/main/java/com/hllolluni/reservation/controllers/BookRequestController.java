package com.hllolluni.reservation.controllers;

import com.hllolluni.reservation.data.entities.BookCirculation;
import com.hllolluni.reservation.enums.ReservationStatus;
import com.hllolluni.reservation.services.BookRequestService;
import com.hllolluni.reservation.transfers.BookCirculationTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BookRequestController {

    private final BookRequestService bookRequestService;

    @PostMapping("/{bookId}")
    @PreAuthorize("hasAuthority('USER')")
    public String borrowRequest(@PathVariable("bookId") Long bookId, @RequestParam("userId") Long userId, @RequestParam("email") String email) throws Exception {
        return this.bookRequestService.borrow(bookId, userId, email).name();
    }

    @PostMapping("/complete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ReservationStatus completeRequest(@RequestBody List<BookCirculationTransfer> reservations) {
        return this.bookRequestService.setCompleted(reservations);
    }

    @GetMapping("/reservations")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<BookCirculation> reservationsForCompletions() throws Exception {
        return this.bookRequestService.getAllUnCompleted();
    }
}
