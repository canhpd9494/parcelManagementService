package com.service.parcelmanagement.domain.parcelmanagement;

import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import com.service.parcelmanagement.domain.guestsmanagement.guest.GuestRepository;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.Parcel;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.ParcelRepository;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.ParcelStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ParcelRequestDomainService {

    private final ParcelRepository repository;
    private final GuestRepository guestRepository;

    public ParcelRequestDomainService(ParcelRepository repository, GuestRepository guestRepository) {
        this.repository = repository;
        this.guestRepository = guestRepository;
    }

    public List<Parcel> findParcels(Integer id, Integer guestId, LocalDate receivedDate, LocalDate pickupDate, String status) {
        if (id != null) return repository.findByParcelId(id);
        return repository.findByAllField(guestId, receivedDate, pickupDate, status);
    }

    public Parcel createParcel(ParcelRequestCreateCommand command) {
        Parcel parcel = new Parcel();
        Guest guest = guestRepository.findByGuestId(command.getGuestId()).get(0);
        parcel.setGuest(guest);
        parcel.setReceivedDate(command.getReceivedDate());
        parcel.setStatus(ParcelStatus.PENDING.getCode());
        parcel.setDescription(command.getDescription());
        return repository.save(parcel);
    }

    public void pickupParcel(Integer parcelId) {
        Parcel parcel = repository.findByParcelId(parcelId).get(0);
        parcel.setStatus(ParcelStatus.COMPLETED.getCode());
        parcel.setPickupDate(LocalDate.now());
        repository.save(parcel);
    }

}
