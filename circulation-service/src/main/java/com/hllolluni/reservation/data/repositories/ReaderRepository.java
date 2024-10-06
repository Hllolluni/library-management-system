package com.hllolluni.reservation.data.repositories;

import com.hllolluni.reservation.data.entities.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Optional<Reader> findReaderByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
}
