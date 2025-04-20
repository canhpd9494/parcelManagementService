package com.service.parcelmanagement.domain.guestsmanagement;

import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import com.service.parcelmanagement.domain.guestsmanagement.guest.GuestRepository;
import com.service.parcelmanagement.domain.guestsmanagement.guest.GuestStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestRequestDomainService {

    private final GuestRepository repository;

    public GuestRequestDomainService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findGuests(Integer id, String name, String gender, Integer roomNumber, String phoneNumber, String email, Long socialId, String status) {
        if (id != null) return repository.findByGuestId(id);
        return repository.findByAllField(name, gender, roomNumber, phoneNumber, email, socialId, status);
    }

    public void checkIn(Integer id, Integer roomNumber) {
        Guest guest = repository.findByGuestId(id).get(0);
        guest.setRoomNumber(roomNumber);
        guest.setStatus(GuestStatus.CHECKIN.getCode());
        repository.save(guest);
    }

    public void checkOut(Integer id) {
        Guest guest = repository.findByGuestId(id).get(0);
        guest.setStatus(GuestStatus.CHECKOUT.getCode());
        repository.save(guest);
    }
}
