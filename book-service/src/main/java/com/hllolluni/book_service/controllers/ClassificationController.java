package com.hllolluni.book_service.controllers;

import com.hllolluni.book_service.services.ClassificationService;
import com.hllolluni.book_service.transfers.ClassificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classification")
@AllArgsConstructor
public class ClassificationController {

    private final ClassificationService classificationService;

    @GetMapping("/classifications")
    public List<ClassificationDTO> getClassifications() {
        return classificationService.getAllClassifications();
    }
}
