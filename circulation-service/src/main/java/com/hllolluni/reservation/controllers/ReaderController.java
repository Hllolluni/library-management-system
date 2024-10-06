package com.hllolluni.reservation.controllers;

import com.hllolluni.reservation.services.ReaderService;
import com.hllolluni.common_module.ReaderTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @PostMapping
    public ReaderTransfer save(@RequestBody ReaderTransfer readerTransfer){
        return this.readerService.save(readerTransfer);
    }
}
