package com.service.parcelmanagement.infra.parcelmanagement;

import com.service.parcelmanagement.app.parcelmanagement.*;
import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestDomainService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ParcelRequestApplicationServiceImpl implements ParcelRequestApplicationService {

    private final ParcelRequestDomainService domainService;
    private final ParcelRequestMapper mapper;

    public ParcelRequestApplicationServiceImpl(ParcelRequestDomainService domainService, ParcelRequestMapper mapper) {
        this.domainService = domainService;
        this.mapper = mapper;
    }

    @Override
    public List<ParcelQueryDto> findParcels(Integer id, Integer guestId, LocalDate receivedDate, LocalDate pickupDate, String status) {
        return mapper.map(domainService.findParcels(id, guestId, receivedDate, pickupDate, status));
    }

    @Override
    public ParcelCreateResponseDto createParcel(ParcelCreateDto dto) {
        return mapper.map(domainService.createParcel(mapper.map(dto)));
    }

    @Override
    public void pickupParcel(Integer parcelId) {
        domainService.pickupParcel((parcelId));
    }

}
