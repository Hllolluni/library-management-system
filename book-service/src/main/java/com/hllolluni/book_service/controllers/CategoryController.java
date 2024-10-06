package com.hllolluni.book_service.controllers;

import com.hllolluni.book_service.services.CategoryService;
import com.hllolluni.book_service.transfers.CategoryTransfer;
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
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryTransfer saveCategory(@RequestBody CategoryTransfer category) throws Exception {
        return categoryService.saveCategory(category);
    }

    @GetMapping("/{id}")
    public CategoryTransfer getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/categories_to_string")
    public List<String> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping
    public CategoryTransfer getCategoryByName(@RequestParam("name") String name) throws Exception {
        return categoryService.getCategoryByName(name);
    }

    @GetMapping("/categories")
    public List<CategoryTransfer> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/classification/{classificationId}")
    public List<CategoryTransfer> getCategoriesByClassificationId(@PathVariable("classificationId") Long classificationId) {
        return categoryService.getCategoriesByClassificationId(classificationId);
    }

    @GetMapping("/book/{bookId}")
    public CategoryTransfer getBookCategoryByBookId(@PathVariable("bookId") Long bookId) throws Exception {
        return categoryService.getCategoryByBookId(bookId);
    }
}
