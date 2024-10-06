package com.hllolluni.reservation.enums;

public enum ReservationStatus {
    CREATED("created"), COMPLETED("completed"), EXPIRED("expired");

    private final String message;

    ReservationStatus(String message){
        this.message = message;
    }
}
