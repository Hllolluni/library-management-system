package com.hllolluni.book_service.controllers;

import com.hllolluni.book_service.services.AuthorService;
import com.hllolluni.book_service.transfers.AuthorTransfer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {

    private AuthorService authorService;

    @PostMapping
    public AuthorTransfer saveAuthor(@RequestBody AuthorTransfer author) throws Exception {
        return authorService.saveAuthor(author);
    }

    @GetMapping("/{id}")
    public AuthorTransfer getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author")
    public AuthorTransfer getAuthorByName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return authorService.getAuthorByName(firstName, lastName);
    }

    @GetMapping
    public List<AuthorTransfer> getAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/book/{bookId}")
    public List<AuthorTransfer> getAuthorsOfBookById(@PathVariable("bookId") Long bookId) {
        return authorService.getAuthorsOfBook(bookId);
    }

    @GetMapping("/category/{categoryId}")
    public List<AuthorTransfer> getAuthorsOfCategoryById(@PathVariable("categoryId") Long categoryId) {
        return authorService.getAuthorsOfCategoryById(categoryId);
    }

    @GetMapping("/classification/{classificationId}")
    public List<AuthorTransfer> getAuthorsOfClassificationById(@PathVariable("classificationId") Long classificationId) {
        return authorService.getAuthorsOfClassificationId(classificationId);
    }
}
