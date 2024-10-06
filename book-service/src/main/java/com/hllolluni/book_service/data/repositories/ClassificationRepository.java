package com.hllolluni.book_service.data.repositories;

import com.hllolluni.book_service.data.entities.Classification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {
    Optional<Classification> findByName(String name);
//    @Query(value = "select * from classification c right join book_classification b on c.id=b.classification_id where b.book_id = ?", nativeQuery = true)
//    Optional<Classification> findClassificationByBookId(Long classificationId);
//    @Query(value = "select * from classification c right join author_classification a on c.id=a.classification_id where a.author_id = ?", nativeQuery = true)
//    List<Classification> findClassificationByAuthorId(Long authorId);
//
//    @Query(value = "select * from classification a right join category_classification b on a.id=b.classification_id where a.category_id = ?", nativeQuery = true)
//    Optional<Classification> findClassificationByCategoryId(Long categoryId);
}
