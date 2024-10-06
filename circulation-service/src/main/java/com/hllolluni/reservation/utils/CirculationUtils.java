package com.hllolluni.reservation.utils;

import com.hllolluni.reservation.data.entities.Reader;
import com.hllolluni.common_module.ReaderStatus;

public class CirculationUtils {
    public static Reader readerConverter(Reader reader, ReaderStatus readerStatus) {
        reader.setReaderStatus(readerStatus);
        return reader;
    }
}
