package com.service.parcelmanagement.domain.parcelmanagement.parcel;

import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "parcel")
public class Parcel {

    @Id
    @Column(name = "parcel_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer parcelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @Column(name = "received_date", nullable = false, updatable = false)
    private LocalDate receivedDate;

    @Column(name = "pick_up_date")
    private LocalDate pickupDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description")
    private String description;

    public Integer getParcelId() {
        return parcelId;
    }

    public void setParcelId(Integer parcelId) {
        this.parcelId = parcelId;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
