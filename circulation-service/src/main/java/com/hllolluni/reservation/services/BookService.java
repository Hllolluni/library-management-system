package com.hllolluni.reservation.services;

import com.hllolluni.reservation.enums.ReservationStatus;

public interface BookService {
    ReservationStatus borrow(Long bookId, Long userId, String email) throws Exception;
//    void sendReminder(Long bookId, String user);
//    void sendReminderWhenExpired(Long bookId, String user);
//    void setCompleted(Long bookId, String user);
}