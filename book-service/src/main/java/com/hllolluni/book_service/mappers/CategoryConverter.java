package com.hllolluni.book_service.mappers;

import com.hllolluni.book_service.transfers.CategoryTransfer;
import com.hllolluni.book_service.data.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Category convertCategoryTransferToCategory(CategoryTransfer categoryTransfer) {
        Category category = modelMapper.map(categoryTransfer, Category.class);
        return category;
    }

    public CategoryTransfer convertCategoryToCategoryTransfer(Category category) {
        CategoryTransfer categoryTransfer = modelMapper.map(category, CategoryTransfer.class);
        categoryTransfer.setClassification(category.getClassification().getName());
        return categoryTransfer;
    }
}
