package com.hllolluni.book_service.transfers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String authors;
    private String isbn;
    private String description;
    private byte[] image;
    private int daysToBeReturn;
    private int numberOfPages;
    private int copiesNumber;
}
