package com.hllolluni.book_service.services;

import com.hllolluni.book_service.transfers.ClassificationDTO;
import com.hllolluni.book_service.data.entities.Classification;
import com.hllolluni.book_service.data.repositories.ClassificationRepository;
import com.hllolluni.book_service.mappers.ClassificationConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassificationService {

    private final ClassificationRepository classificationRepository;
    private ClassificationConverter classificationConverter;

//    public ClassificationDTO saveClassification(ClassificationDTO classificationDTO) {
//        Classification classification = Classification.builder()
//                .name(classificationDTO.getName())
//                .build();
//
//        return classificationConverter.convertToClassificationDTO(classificationRepository.save(classification));
//    }
//
//    public ClassificationDTO getClassificationById(Long id) {
//        Optional<Classification> classification = classificationRepository.findById(id);
//
//        if (classification.isPresent()) {
//            return classificationConverter.convertToClassificationDTO(classification.get());
//        }
//
//        return null;
//    }
//
//    public ClassificationDTO getClassificationByName(String name) {
//        Optional<Classification> classification = classificationRepository.findByName(name);
//
//        if (classification.isPresent()) {
//            return classificationConverter.convertToClassificationDTO(classification.get());
//        }
//
//        return null;
//    }
//
//    public Classification getClassificationEntityByName(String name) {
//        Optional<Classification> classification = classificationRepository.findByName(name);
//
//        if (classification.isPresent()) {
//            return classification.get();
//        }
//
//        return null;
//    }
//
//    public void deleteClassification(Long classificationId) {
//        classificationRepository.deleteById(classificationId);
//    }
//
//    public void deleteClassification(Classification classification) {
//        classificationRepository.delete(classification);
//
//
//    }
//
//    public ClassificationDTO getClassificationByBookId(Long classificationId) {
//        Optional<Classification> classificationOptional = classificationRepository.findClassificationByBookId(classificationId);
//
//        if (classificationOptional.isPresent()) {
//            return classificationConverter.convertToClassificationDTO(classificationOptional.get());
//        }
//
//        return null;
//    }
//
    public List<ClassificationDTO> getAllClassifications() {
        return classificationRepository.findAll()
                .stream()
                .map(classification -> classificationConverter.convertToClassificationDTO(classification))
                .collect(Collectors.toList());
    }
//
//    public List<ClassificationDTO> getClassificationByAuthorId(Long authorId) {
//        List<Classification> classifications = classificationRepository.findClassificationByAuthorId(authorId);
//
//        return classifications.stream()
//                .map(classification -> classificationConverter.convertToClassificationDTO(classification))
//                .collect(Collectors.toList());
//    }
//
//    public ClassificationDTO getClassificationByCategoryId(Long categoryId) {
//        Optional<Classification> classificationOptional = classificationRepository.findClassificationByCategoryId(categoryId);
//
//        if (classificationOptional.isPresent()) {
//            return classificationConverter.convertToClassificationDTO(classificationOptional.get());
//        }
//
//        return null;
//    }

    public void updateClassificationByEntity(Classification classification) {
        this.classificationRepository.save(classification);
    }
}
