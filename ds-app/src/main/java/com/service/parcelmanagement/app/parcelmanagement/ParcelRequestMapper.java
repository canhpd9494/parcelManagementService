package com.service.parcelmanagement.app.parcelmanagement;

import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestCreateCommand;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.Parcel;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.ParcelStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ParcelRequestMapper {
    public ParcelRequestCreateCommand map(ParcelCreateDto dto) {
        return new ParcelRequestCreateCommand(dto.getGuestId(), dto.getReceivedDate(), dto.getDescription());
    }

    public List<ParcelQueryDto> map(List<Parcel> entities) {
        if (entities.size() == 1 && entities.get(0).getParcelId() == null) {
            return new ArrayList<>();
        }

        List<ParcelQueryDto> dtos = new ArrayList<>();

        for(Parcel p : entities) {
            ParcelQueryDto dto = new ParcelQueryDto();
            dto.setId(p.getParcelId());
            dto.setGuestId(p.getGuest().getGuestId());
            dto.setReceivedDate(p.getReceivedDate());
            dto.setPickupDate(p.getPickupDate());
            if (Objects.nonNull(p.getStatus())) {
                dto.setStatus(ParcelStatus.fromCode(p.getStatus()).toString());
            } else {
                dto.setStatus(null);
            }
            dto.setDescription(p.getDescription());
            dtos.add(dto);
        }

        return dtos;
    }

    public ParcelCreateResponseDto map(Parcel entity) {
        return new ParcelCreateResponseDto(entity.getParcelId(),
                entity.getGuest().getGuestId(),
                entity.getReceivedDate(),
                ParcelStatus.fromCode(entity.getStatus()).toString(),
                entity.getDescription());
    }
}
