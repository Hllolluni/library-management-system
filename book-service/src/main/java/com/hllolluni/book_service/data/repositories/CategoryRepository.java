package com.hllolluni.book_service.data.repositories;

import com.hllolluni.book_service.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query(value = "select c from Category c right join c.bookSet b where b.id = :bookId")
    Optional<Category> findCategoryByBookId(@Param("bookId") Long bookId);
    List<Category> findCategoriesByClassificationId(Long classificationId);

    @Query(value = "select c.name from category c", nativeQuery = true)
    List<String> findAllNames();
}
