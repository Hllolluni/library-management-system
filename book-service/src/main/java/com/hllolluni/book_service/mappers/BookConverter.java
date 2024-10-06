package com.hllolluni.book_service.mappers;

import com.hllolluni.book_service.transfers.requests.BookRequest;
import com.hllolluni.book_service.data.entities.Book;
import com.hllolluni.book_service.transfers.BookResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Book convertBookRequestToBook(BookRequest bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        return book;
    }

    public BookResponse convertBookEntityToBookResponse(Book book) {
        BookResponse bookResponse = modelMapper.map(book, BookResponse.class);

        if (book.getAuthors() != null){
            List<String> authorsList = book.getAuthors().stream()
                    .map(author -> author.getFirstName() + " " + author.getLastName())
                    .collect(Collectors.toList());
            String authors = "Authors: " + String.join(", ", new ArrayList<String>(authorsList));
            if (authors.length() >= 38){
                bookResponse.setAuthors(authors.substring(0, 34) + " ...");
            }else {
                bookResponse.setAuthors(authors);
            }
        }
        return bookResponse;
    }

}
