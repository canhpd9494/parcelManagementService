package com.service.parcelmanagement.infra.parcelmanagement;

import com.service.parcelmanagement.domain.parcelmanagement.parcel.Parcel;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.ParcelRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ParcelRepositoryImpl implements ParcelRepository {
    private final ParcelJpaRepository jpaRepository;

    public ParcelRepositoryImpl(ParcelJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Parcel> findByParcelId(Integer id) {

        return List.of(jpaRepository.findById(id).orElse(new Parcel()));
    }

    @Override
    public List<Parcel> findByAllField(Integer guestId, LocalDate receivedDate, LocalDate pickupDate, String status) {
        return jpaRepository.findByAllField(guestId, receivedDate, pickupDate, status);
    }

    @Override
    public Parcel save(Parcel parcel) {
        return jpaRepository.save(parcel);
    }
}
