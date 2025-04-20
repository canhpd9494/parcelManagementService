package com.service.parcelmanagement.infra.guestmanagement;

import com.service.parcelmanagement.app.guestmanagement.GuestQueryDto;
import com.service.parcelmanagement.app.guestmanagement.GuestRequestApplicationService;
import com.service.parcelmanagement.app.guestmanagement.GuestRequestMapper;
import com.service.parcelmanagement.app.guestmanagement.GuestSearchDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelRequestMapper;
import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestDomainService;
import com.service.parcelmanagement.domain.guestsmanagement.GuestRequestDomainService;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.ParcelStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestRequestApplicationServiceImpl implements GuestRequestApplicationService {
    private final GuestRequestMapper guestRequestMapper;
    private final GuestRequestDomainService guestRequestDomainService;
    private final ParcelRequestDomainService parcelRequestDomainService;
    private final ParcelRequestMapper parcelRequestMapper;

    public GuestRequestApplicationServiceImpl(GuestRequestMapper guestRequestMapper, GuestRequestDomainService guestRequestDomainService, ParcelRequestDomainService parcelRequestDomainService, ParcelRequestMapper parcelRequestMapper) {
        this.guestRequestMapper = guestRequestMapper;
        this.guestRequestDomainService = guestRequestDomainService;
        this.parcelRequestDomainService = parcelRequestDomainService;
        this.parcelRequestMapper = parcelRequestMapper;
    }

    @Override
    public List<GuestQueryDto> findGuests(GuestSearchDto dto) {
        return guestRequestMapper.map(guestRequestDomainService.findGuests(dto.getId(), dto.getName(), dto.getGender(), dto.getRoomNumber(), dto.getPhoneNumber(), dto.getEmail(), dto.getSocialId(), dto.getStatus()));
    }

    @Override
    public List<ParcelQueryDto> getPendingParcels(Integer id) {
        return parcelRequestMapper.map(parcelRequestDomainService.findParcels(null, id, null, null, ParcelStatus.PENDING.getCode()));
    }
}
