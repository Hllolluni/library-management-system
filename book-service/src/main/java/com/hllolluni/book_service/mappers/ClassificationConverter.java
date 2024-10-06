package com.hllolluni.book_service.mappers;

import com.hllolluni.book_service.transfers.ClassificationDTO;
import com.hllolluni.book_service.data.entities.Classification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassificationConverter {
    @Autowired
    private ModelMapper modelMapper;

    public Classification convertToClassification(ClassificationDTO classificationDTO) {
        Classification classification = modelMapper.map(classificationDTO, Classification.class);
        return classification;
    }

    public ClassificationDTO convertToClassificationDTO(Classification classification) {
        ClassificationDTO classificationDTO = modelMapper.map(classification, ClassificationDTO.class);

        if (classification.getCategories() != null){
            List<String> categoriesList = classification.getCategories().stream()
                    .map(category -> category.getName())
                    .collect(Collectors.toList());
            classificationDTO.setCategories(categoriesList);
        }

        return classificationDTO;
    }
}
