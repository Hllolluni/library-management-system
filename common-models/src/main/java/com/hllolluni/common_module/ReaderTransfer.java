package com.hllolluni.common_module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReaderTransfer {
    private String firstName;
    private String lastName;
    private String email;
    private ReaderStatus readerStatus;
}