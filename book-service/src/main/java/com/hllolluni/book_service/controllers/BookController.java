package com.hllolluni.book_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hllolluni.book_service.services.BookService;
import com.hllolluni.book_service.transfers.BookResponse;
import com.hllolluni.book_service.transfers.requests.BookDeletionDTO;
import com.hllolluni.book_service.transfers.requests.BookRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookResponse saveBook(@RequestParam("image") MultipartFile image, @RequestParam("request") String request) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookRequest bookRequest = objectMapper.readValue(request, BookRequest.class);
        return bookService.saveBook(image, bookRequest);
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable("id") Long id) throws Exception {
        return bookService.getBookById(id);
    }

    @GetMapping("/book")
    public BookResponse getBookByTitle(@RequestParam("title") String title) throws Exception {
        return bookService.getBookByTitle(title);
    }

    @GetMapping
    public List<BookResponse> getBooks() {
        var books =  bookService.getAllBooks();

        return books;
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBookById(bookId);
    }

    @DeleteMapping
    public void deleteBookByTitleAndIsbn(@RequestBody BookDeletionDTO bookDeletionDTO) throws Exception {
        bookService.deleteBookByTitleAndIsbn(bookDeletionDTO);
    }

    @GetMapping("/author/{authorId}")
    public List<BookResponse> getBooksByAuthorId(@PathVariable("authorId") Long authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }

    @GetMapping("/category/{categoryId}")
    public List<BookResponse> getBooksByCategory(@PathVariable("categoryId") Long categoryId) {
        return bookService.getBooksByCategoryId(categoryId);
    }

    @GetMapping("/category")
    public List<BookResponse> getBooksByCategory(@RequestParam("categoryName") String categoryName) throws Exception {
        return bookService.getBooksByCategoryName(categoryName);
    }

    @GetMapping("/classification/{classificationId}")
    public List<BookResponse> getBooksByClassificationId(@PathVariable("classificationId") Long classificationId) {
        return bookService.getBooksByClassificationId(classificationId);
    }

    @PostMapping("/decreaseCopiesNumber")
    public void decreaseBookNumber(@RequestParam(value = "isbn") String isbn, @RequestParam(value = "copiesNumber") String copiesNumber){
        this.bookService.decreaseAvailableCopies(isbn, Integer.valueOf(copiesNumber));
    }
}