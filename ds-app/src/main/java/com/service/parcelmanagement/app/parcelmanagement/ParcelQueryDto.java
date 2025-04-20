package com.service.parcelmanagement.app.parcelmanagement;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "ParcelQueryDto")
public class ParcelQueryDto {
    @Schema(description = "Parcel Id")
    private Integer id;

    @Schema(description = "Guest Id")
    private Integer guestId;

    @Schema(description = "Parcel Received Date")
    private LocalDate receivedDate;

    @Schema(description = "Parcel Pickup Date")
    private LocalDate pickupDate;

    @Schema(description = "Parcel Status (PENDING/COMPLETE")
    private String status;

    @Schema(description = "Parcel Description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }
}
