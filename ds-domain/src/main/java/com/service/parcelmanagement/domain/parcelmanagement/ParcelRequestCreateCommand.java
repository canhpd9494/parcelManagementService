package com.service.parcelmanagement.domain.parcelmanagement;

import java.time.LocalDate;

public class ParcelRequestCreateCommand {

    public ParcelRequestCreateCommand(Integer guestId, LocalDate receivedDate, String description) {
        this.guestId = guestId;
        this.receivedDate = receivedDate;
        this.description = description;
    }

    private final Integer guestId;
    private final LocalDate receivedDate;
    private final String description;

    public Integer getGuestId() {
        return guestId;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public String getDescription() {
        return description;
    }
}
