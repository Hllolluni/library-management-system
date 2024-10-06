package com.hllolluni.reservation.mappers;

import com.hllolluni.reservation.data.entities.Reader;
import com.hllolluni.common_module.ReaderTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReaderConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Reader convertReaderTransferToReader(ReaderTransfer readerTransfer) {
        Reader reader = modelMapper.map(readerTransfer, Reader.class);
        return reader;
    }

    public ReaderTransfer convertReaderEntityToReaderTransfer(Reader reader) {
        ReaderTransfer readerTransfer = modelMapper.map(reader, ReaderTransfer.class);
        return readerTransfer;
    }
}
