package com.service.parcelmanagement.infra.guestmanagement;

import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import com.service.parcelmanagement.domain.guestsmanagement.guest.GuestRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GuestRepositoryImpl implements GuestRepository {
    private final GuestJpaRepository jpaRepository;

    public GuestRepositoryImpl(GuestJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Guest> findByGuestId(Integer id) {
        return List.of(jpaRepository.findById(id).orElse(new Guest()));
    }

    @Override
    public List<Guest> findByAllField(String name, String gender, Integer roomNumber, String phoneNumber, String email, Long socialId, String status) {
        return jpaRepository.findByAllField(name, gender, roomNumber, phoneNumber, email, socialId, status);
    }

    @Override
    public void save(Guest guest) {
        jpaRepository.save(guest);
    }
}
