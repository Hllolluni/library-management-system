package com.hllolluni.reservation.mappers;

import com.hllolluni.reservation.data.entities.BookCirculation;
import com.hllolluni.reservation.transfers.BookCirculationTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {

    @Autowired
    private ModelMapper modelMapper;

    public BookCirculation convertBookCirculationTransferToBookCirculation(BookCirculationTransfer bookCirculationTransfer) {
        BookCirculation bookCirculation = modelMapper.map(bookCirculationTransfer, BookCirculation.class);
        return bookCirculation;
    }

    public BookCirculationTransfer convertBookCirculationEntityToBookCirculationTransfer(BookCirculation bookCirculation) {
        BookCirculationTransfer bookCirculationTransfer = modelMapper.map(bookCirculation, BookCirculationTransfer.class);
        return bookCirculationTransfer;
    }
}
