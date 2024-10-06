package com.hllolluni.reservation.services;

import com.hllolluni.common_module.kafka.EmailModel;
import com.hllolluni.common_module.kafka.KafkaProducerService;
import com.hllolluni.reservation.data.entities.BookCirculation;
import com.hllolluni.reservation.data.repositories.BookReservationRepository;
import com.hllolluni.reservation.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableAsync
@RequiredArgsConstructor
public class ReservationSchedulerService {
    private final BookReservationRepository bookReservationRepository;
    private final KafkaProducerService kafkaProducerService;

    @Scheduled(cron = "0 1 0 * * *")
    public void scheduleFixedDelayTask() {
        System.out.println("Executed!");
        LocalDateTime currentDate = LocalDateTime.now();
        List<BookCirculation> bookCirculations = bookReservationRepository.findAllExpired(currentDate);
        bookCirculations.stream()
            .forEach(bookCirculation ->{
                if(Duration.between(currentDate, bookCirculation.getReturnDate()).toDays() >= 7){
                    bookCirculation.setStatus(ReservationStatus.EXPIRED);
                    this.bookReservationRepository.save(bookCirculation);
                }
                this.sendExpirationEmail(bookCirculation);
            });
    }

    private void sendExpirationEmail(BookCirculation reservation) {
        String subject = "Your Book Reservation Has Expired!";
        String body = " Dear " + reservation.getFirstName() + ", \n"
                + " We hope you have enjoyed reading " + reservation.getBookTitle() + ". This is a friendly reminder that the \n" +
                "borrowing period for this book has expired." +
                "\n" + "Please return the book to library at your earliest convenience." +
                "\n\n" + "Best regards, " +
                "\n" + "Library Management!";
        kafkaProducerService.sendMessage(new EmailModel(reservation.getEmail(), subject, body));
    }
}
