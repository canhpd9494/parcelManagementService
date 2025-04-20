package com.service.parcelmanagement.infra.parcelmanagement;

import com.service.parcelmanagement.domain.parcelmanagement.parcel.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ParcelJpaRepository extends JpaRepository<Parcel, Integer> {


    @Query(value = "SELECT p.* FROM parcel as p WHERE (?1 IS NULL OR guest_id = ?1) AND (?2 IS NULL OR received_date >= ?2) AND (?3 IS NULL OR pick_up_date <= ?3) AND (?4 IS NULL OR status = ?4) ORDER BY received_date DESC", nativeQuery = true)
    List<Parcel> findByAllField(Integer guestId, LocalDate receivedDate, LocalDate pickupDate, String status);

}
