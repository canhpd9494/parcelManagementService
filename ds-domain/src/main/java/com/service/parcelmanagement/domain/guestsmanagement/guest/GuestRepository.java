package com.service.parcelmanagement.domain.guestsmanagement.guest;

import java.util.List;
public interface GuestRepository {
    List<Guest> findByGuestId(Integer id);
    List<Guest> findByAllField(String name, String gender, Integer roomNumber, String phoneNumber, String email, Long socialId, String status);
    void        save(Guest guest);
}
