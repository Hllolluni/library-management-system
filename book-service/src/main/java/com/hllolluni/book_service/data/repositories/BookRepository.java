package com.hllolluni.book_service.data.repositories;

import com.hllolluni.book_service.data.entities.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByTitle(String title);

    void deleteByTitleAndIsbn(String title, String isbn);

    Optional<Book> findByTitleAndIsbn(String title, String isbn);

    @Query(value = "select * from book b right join book_author a on b.id=a.book_id where a.author_id = ?", nativeQuery = true)
    List<Book> findBooksByAuthorsContaining(Long authorId);

    @Query(value = "select b from Book b where b.category.id = :categoryId")
    List<Book> findBooksByCategoryContaining(@Param("categoryId") Long categoryId);

    @Query(value = "select b from Book b where b.category.name = :name")
    List<Book> findBooksByCategoryName(@Param("name") String name);

    @Query(value = "select b from Book b where b.classification.id = :classificationId")
    List<Book> findBooksByClassificationContaining(@Param("classificationId") Long classificationId);


    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copiesNumber = :newNumberOfCopies WHERE b.isbn = :isbn")
    void updateNumberOfCopiesById(@Param("isbn") String isbn, @Param("newNumberOfCopies") int newNumberOfCopies);
}
