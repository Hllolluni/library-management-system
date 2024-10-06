package com.hllolluni.book_service.data.repositories;

import com.hllolluni.book_service.data.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "select * from author a right join book_author b on a.id=b.author_id where b.book_id = ?", nativeQuery = true)
    List<Author> findAuthorsByBook(Long bookId);

    @Query(value = "select * from author a right join category_author b on a.id=b.author_id where b.category_id = ?", nativeQuery = true)
    List<Author> findAuthorsByCategory(Long categoryId);

    @Query(value = "select * from author a right join classification_author b on a.id=b.author_id where b.classification_id = ?", nativeQuery = true)
    List<Author> findAuthorsByClassification(Long classificationId);
}
