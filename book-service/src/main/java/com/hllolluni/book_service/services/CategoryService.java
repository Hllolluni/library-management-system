package com.hllolluni.book_service.services;

import com.hllolluni.book_service.data.entities.Category;
import com.hllolluni.book_service.data.entities.Classification;
import com.hllolluni.book_service.data.repositories.CategoryRepository;
import com.hllolluni.book_service.data.repositories.ClassificationRepository;
import com.hllolluni.book_service.mappers.CategoryConverter;
import com.hllolluni.book_service.transfers.CategoryTransfer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ClassificationRepository classificationRepository;
    private final CategoryConverter categoryConverter;

    public CategoryTransfer saveCategory(CategoryTransfer categoryTransfer) throws Exception {
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryTransfer.getName());
        Optional<Classification> classificationOptional = classificationRepository.findByName(categoryTransfer.getClassification());

        if (categoryOptional.isEmpty() && classificationOptional.isPresent()) {
            Classification classification = classificationOptional.get();

            Category category = Category.builder()
                    .name(categoryTransfer.getName())
                    .classification(classification)
                    .build();

            return categoryConverter.convertCategoryToCategoryTransfer(categoryRepository.save(category));
        }

        throw new Exception();
    }

    public CategoryTransfer getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        return categoryOptional.map(categoryConverter::convertCategoryToCategoryTransfer).orElse(null);
    }

    public CategoryTransfer getCategoryByName(String name) throws Exception {
        Optional<Category> categoryOptional = categoryRepository.findByName(name);

        if (categoryOptional.isPresent()) {
            return categoryConverter.convertCategoryToCategoryTransfer(categoryOptional.get());
        }
        throw new Exception();
    }

    public Category getCategoryEntityByName(String name) throws Exception {
        Optional<Category> categoryOptional = categoryRepository.findByName(name);

        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        }
        throw new Exception();
    }

    public void updateCategoryByEntity(Category category) {
        categoryRepository.save(category);
    }

    public List<CategoryTransfer> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryConverter::convertCategoryToCategoryTransfer)
                .collect(Collectors.toList());
    }

    public CategoryTransfer getCategoryByBookId(Long bookId) throws Exception {
        Optional<Category> categoryOptional = categoryRepository.findCategoryByBookId(bookId);

        if (categoryOptional.isPresent()) {
            return categoryConverter.convertCategoryToCategoryTransfer(categoryOptional.get());
        }

        throw new Exception();
    }

    public List<CategoryTransfer> getCategoriesByClassificationId(Long classificationId) {
        List<Category> categories = categoryRepository.findCategoriesByClassificationId(classificationId);

        return categories.stream()
                .map(categoryConverter::convertCategoryToCategoryTransfer)
                .collect(Collectors.toList());
    }

    public List<String> getCategories() {
        return this.categoryRepository.findAllNames();
    }
}
