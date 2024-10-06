package com.hllolluni.book_service.helpers;

import com.hllolluni.book_service.data.repositories.ClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Bootstrapclass implements CommandLineRunner {

//    @Autowired
//    private CategoryRepository categoryRepository;
    @Autowired
    private ClassificationRepository classificationRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        Classification firstClassification = Classification.builder()
//                .name("Science and engineering")
//                .build();
//        Classification secondClassification = Classification.builder()
//                .name("Arts and social sciences")
//                .build();
//
//        Category firstCategory = Category.builder()
//                .name("Computer Science And Engineering")
//                .build();
//        Category secondCategory = Category.builder()
//                .name("Physics")
//                .build();
//        firstClassification.setCategories(Arrays.asList(firstCategory, secondCategory));
//
//        Category thirdCategory = Category.builder()
//                .name("Visual Arts")
//                .build();
//        Category fourthCategory = Category.builder()
//                .name("Sociology")
//                .build();
//        secondClassification.setCategories(Arrays.asList(thirdCategory, fourthCategory));
//
//
//        classificationRepository.saveAndFlush(firstClassification);
//        classificationRepository.saveAndFlush(secondClassification);
    }
}
