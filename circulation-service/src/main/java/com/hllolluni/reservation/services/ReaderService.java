package com.hllolluni.reservation.services;

import com.hllolluni.reservation.data.entities.Reader;
import com.hllolluni.reservation.data.repositories.ReaderRepository;
import com.hllolluni.reservation.mappers.ReaderConverter;
import com.hllolluni.common_module.ReaderTransfer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final ReaderConverter readerConverter;

    public ReaderTransfer save(ReaderTransfer readerTransfer) {
        Reader reader = Reader.builder()
                .firstName(readerTransfer.getFirstName())
                .lastName(readerTransfer.getLastName())
                .email(readerTransfer.getEmail())
                .readerStatus(readerTransfer.getReaderStatus())
                .build();
        return readerConverter.convertReaderEntityToReaderTransfer(readerRepository.save(reader));
    }

    public ReaderTransfer save(Reader reader) {
        return readerConverter.convertReaderEntityToReaderTransfer(readerRepository.save(reader));
    }

    public Reader getReaderById(Long userId) throws Exception {
        Optional<Reader> readerOptional = readerRepository.findById(userId);
        if (readerOptional.isPresent()) {
            Reader reader = readerOptional.get();
            return reader;
        }
        throw new Exception();
    }

    public Reader getReaderByNameAndEmail(String firstName, String lastName, String email) throws Exception {
        Optional<Reader> readerOptional = this.readerRepository.findReaderByFirstNameAndLastNameAndEmail(firstName, lastName, email);
        if (readerOptional.isPresent()){
            return readerOptional.get();
        }
        throw new Exception();
    }
}
