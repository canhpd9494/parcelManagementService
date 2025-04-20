package com.service.parcelmanagement.app.parcelmanagement;

import java.time.LocalDate;
import java.util.List;

public interface ParcelRequestApplicationService {

    List<ParcelQueryDto> findParcels(Integer id, Integer guestId, LocalDate receivedDate, LocalDate pickupDate, String status);

    ParcelCreateResponseDto createParcel(ParcelCreateDto dto);

    void pickupParcel(Integer parcelId);
}
