package com.service.parcelmanagement.app.parcelmanagement;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "ParcelCreateResponseDto")
public class ParcelCreateResponseDto {
    public ParcelCreateResponseDto(Integer parcelId, Integer guestId, LocalDate receivedDate, String status, String description) {
        this.parcelId = parcelId;
        this.guestId = guestId;
        this.receivedDate = receivedDate;
        this.status = status;
        this.description = description;
    }
    @Schema(description = "Parcel Id")
    private Integer parcelId;

    @Schema(description = "Guest Id")
    private Integer guestId;

    @Schema(description = "Parcel Received Date")
    private LocalDate receivedDate;

    @Schema(description = "Parcel Status")
    private String status;

    @Schema(description = "Parcel Status (PENDING/COMPLETE")
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

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
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
}
