package com.service.parcelmanagement.app.guestmanagement;

import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;

import java.util.List;

public interface GuestRequestApplicationService {
    public List<GuestQueryDto> findGuests(GuestSearchDto dto);
    public List<ParcelQueryDto> getPendingParcels(Integer id);
}
