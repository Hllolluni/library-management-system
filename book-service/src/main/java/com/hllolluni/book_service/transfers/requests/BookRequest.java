package com.hllolluni.book_service.transfers.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookRequest {
    private String title;
    private List<String> authors;
    private String isbn;
    private String description;
    private int copiesNumber;
    private int daysToBeReturn;
    private int pages;
    private String category;
}
