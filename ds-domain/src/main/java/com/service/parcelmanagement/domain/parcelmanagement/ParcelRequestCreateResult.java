package com.service.parcelmanagement.domain.parcelmanagement;

import java.time.LocalDate;

public class ParcelRequestCreateResult {
    private Integer parcelId;
    private Integer guestId;
    private LocalDate receivedDate;
    private LocalDate pickupDate;
    private String description;

    public Integer getParcelId() {
        return parcelId;
    }

    public void setParcelId(Integer parcelId) {
        this.parcelId = parcelId;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceiveDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
