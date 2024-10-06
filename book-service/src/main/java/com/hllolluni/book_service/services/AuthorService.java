package com.hllolluni.book_service.services;

import com.hllolluni.book_service.transfers.AuthorTransfer;
import com.hllolluni.book_service.data.entities.Author;
import com.hllolluni.book_service.data.repositories.AuthorRepository;
import com.hllolluni.book_service.mappers.AuthorConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;

    private AuthorConverter authorConverter;

    public AuthorTransfer saveAuthor(AuthorTransfer authorTransfer) throws Exception {
        Optional<Author> authorOptional = authorRepository.findAuthorByFirstNameAndLastName(authorTransfer.getFirstName(), authorTransfer.getLastName());

        if (!authorOptional.isPresent()){
            Author author = Author.builder()
                    .firstName(authorTransfer.getFirstName())
                    .lastName(authorTransfer.getLastName())
                    .build();

            return authorConverter.convertAuthorEntityToAuthorTransfer(authorRepository.save(author));
        }
        throw new Exception();
    }

    public List<Author> saveAll(List<AuthorTransfer> authorList) {
        List<Author> authors = authorList.stream()
                .map(authorTransfer -> authorConverter.convertAuthorTransferToAuthor(authorTransfer))
                .collect(Collectors.toList());

        return authorRepository.saveAll(authors);
    }

    public AuthorTransfer getAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            return authorConverter.convertAuthorEntityToAuthorTransfer(authorOptional.get());
        }

        return null;
    }

    public AuthorTransfer getAuthorByName(String firstName, String lastName) {
        Optional<Author> authorOptional = authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);

        if (authorOptional.isPresent()) {
            return authorConverter.convertAuthorEntityToAuthorTransfer(authorOptional.get());
        }
        return null;
    }

    public Author getAuthorObjectByName(String firstName, String lastName) {
        Optional<Author> authorOptional = authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);

        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }

        return null;
    }

    public List<AuthorTransfer> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(author -> authorConverter.convertAuthorEntityToAuthorTransfer(author))
                .collect(Collectors.toList());
    }

    public List<AuthorTransfer> getAuthorsOfBook(Long bookId) {
        List<Author> authors = authorRepository.findAuthorsByBook(bookId);

        return authors.stream()
                .map(author -> authorConverter.convertAuthorEntityToAuthorTransfer(author))
                .collect(Collectors.toList());
    }

    public List<AuthorTransfer> getAuthorsOfCategoryById(Long categoryId) {
        List<Author> authors = authorRepository.findAuthorsByCategory(categoryId);

        return authors.stream()
                .map(author -> authorConverter.convertAuthorEntityToAuthorTransfer(author))
                .collect(Collectors.toList());
    }

    public List<AuthorTransfer> getAuthorsOfClassificationId(Long classificationId) {
        List<Author> authors = authorRepository.findAuthorsByClassification(classificationId);

        return authors.stream()
                .map(author -> authorConverter.convertAuthorEntityToAuthorTransfer(author))
                .collect(Collectors.toList());
    }
}
