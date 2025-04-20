package com.service.parcelmanagement.app.guestmanagement;

import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import com.service.parcelmanagement.domain.guestsmanagement.guest.GuestStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GuestRequestMapper {
    public List<GuestQueryDto> map(List<Guest> entities) {
        if (entities.size() == 1 && entities.get(0).getGuestId() == null) {
            return new ArrayList<>();
        }
        List<GuestQueryDto> dtos = new ArrayList<>();
        for(Guest g : entities) {
            GuestQueryDto dto = new GuestQueryDto();
            dto.setId(g.getGuestId());
            dto.setName(g.getName());
            dto.setGender(g.getGender());
            dto.setRoomNumber(g.getRoomNumber());
            dto.setPhoneNumber(g.getPhoneNumber());
            if (Objects.nonNull(g.getStatus())) {
                dto.setStatus(GuestStatus.fromCode(g.getStatus()).toString());
            } else {
                dto.setStatus(null);
            }
            dto.setStatus(g.getStatus());
            dtos.add(dto);
        }

        return dtos;
    }
}
