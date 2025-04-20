package com.service.parcelmanagement.domain.parcelmanagement.parcel;


import java.time.LocalDate;
import java.util.List;

public interface ParcelRepository {
    List<Parcel> findByParcelId(Integer id);
    List<Parcel> findByAllField(Integer guestId, LocalDate receivedDate, LocalDate pickupDate, String status);
    Parcel save(Parcel parcel);
}
