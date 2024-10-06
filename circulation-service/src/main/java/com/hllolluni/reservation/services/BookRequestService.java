package com.hllolluni.reservation.services;

import com.hllolluni.common_module.kafka.EmailModel;
import com.hllolluni.common_module.kafka.KafkaProducerService;
import com.hllolluni.reservation.data.entities.BookCirculation;
import com.hllolluni.reservation.data.entities.Reader;
import com.hllolluni.reservation.data.repositories.BookReservationRepository;
import com.hllolluni.common_module.ReaderStatus;
import com.hllolluni.reservation.enums.ReservationStatus;
import com.hllolluni.reservation.mappers.ReservationConverter;
import com.hllolluni.reservation.transfers.BookCirculationTransfer;
import com.hllolluni.reservation.transfers.BookTransfer;
import com.hllolluni.common_module.ReaderTransfer;
import com.hllolluni.reservation.utils.CirculationUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookRequestService implements BookService {
    private final BookReservationRepository bookReservationRepository;
    private final RestTemplate restTemplate;
    private final KafkaProducerService kafkaProducerService;
    private final ReaderService readerService;
    private final ReservationConverter reservationConverter;

    @Override
    @Transactional
    public ReservationStatus borrow(Long bookId, Long userId, String email) throws Exception {
        Boolean paymentCheck = checkForPayment(userId, email);

        if (paymentCheck) {
            BookTransfer book = restTemplate.getForObject("http://localhost:8080/api/books/" + bookId, BookTransfer.class);
            Reader user = readerService.getReaderById(userId);

            if (book != null && book.copiesNumber() >= 1) {
                if (user.getReaderStatus().equals(ReaderStatus.AVAILABLE)) {
                    Reader reader = CirculationUtils.readerConverter(user, ReaderStatus.READING);
                    BookCirculation bookCirculation = BookCirculation.builder()
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .bookIsbn(book.isbn())
                            .bookTitle(book.title())
                            .startingDate(LocalDateTime.now())
                            .returnDate(LocalDateTime.now().plusDays(book.daysToBeReturn()))
                            .status(ReservationStatus.CREATED)
                            .build();
                    bookReservationRepository.save(bookCirculation);
                    ReaderTransfer readerTransfer = readerService.save(reader);

                    MultiValueMap<String, String> urlVariables = new LinkedMultiValueMap<String, String>();
                    urlVariables.add("isbn", bookCirculation.getBookIsbn());
                    urlVariables.add("copiesNumber", String.valueOf(book.copiesNumber() - 1));
                    restTemplate.postForObject("http://localhost:8080/api/books/decreaseCopiesNumber?", urlVariables, Void.class);
                    this.sendConfirmationEmail(readerTransfer, book.title());
                    return ReservationStatus.CREATED;
                } else {
                    throw new Exception();
                }
            }
        }
        throw new Exception();
    }

    private Boolean checkForPayment(Long userId, String email) {
        String url = "http://localhost:8085/api/payment/checkPayment";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", userId)
                .queryParam("email", email);

        String uri = builder.toUriString();
        Boolean paymentCheck = restTemplate.getForObject(uri, Boolean.class);
        return paymentCheck;
    }

    private void sendConfirmationEmail(ReaderTransfer reader, String title) {
        String subject = "Your Book Reservation is Ready for Pickup!";
        String body = " Dear " + reader.getFirstName() + ", \n"
                + " We are pleased to inform you that your reservation for the book: " + title + " has been successfully processed." +
                "\n" + " The book is now available for you to pick up at your convenience." +
                "\n" + " Please visit our store, during our operating hours, to collect your reserved book." +
                "\n" + " Thank you for choosing our Library. We look forward to serving you!" +
                "\n\n" + "Best regards, " +
                "\n" + "Library management!";
        kafkaProducerService.sendMessage(new EmailModel(reader.getEmail(), subject, body));
    }

    @Transactional
    public ReservationStatus setCompleted(List<BookCirculationTransfer> reservationTransfers) {
        List<BookCirculation> reservations = reservationTransfers.stream()
                .map(reservationTransfer -> {
                    reservationTransfer.setStatus(ReservationStatus.COMPLETED);
                    return this.reservationConverter.convertBookCirculationTransferToBookCirculation(reservationTransfer);
                }).collect(Collectors.toList());

        bookReservationRepository.saveAll(reservations);
        return ReservationStatus.COMPLETED;
    }

    public List<BookCirculation> getAllUnCompleted() {
        return bookReservationRepository.findAllUnCompleted();
    }
}
