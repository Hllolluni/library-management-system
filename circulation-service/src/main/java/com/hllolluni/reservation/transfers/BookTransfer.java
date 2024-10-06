package com.hllolluni.reservation.transfers;

import lombok.Builder;


@Builder
public record BookTransfer(String title, String isbn, int daysToBeReturn, int numberOfPages, int copiesNumber) {
}
