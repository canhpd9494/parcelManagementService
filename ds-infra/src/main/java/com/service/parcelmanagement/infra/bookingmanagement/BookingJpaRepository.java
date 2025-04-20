package com.service.parcelmanagement.infra.bookingmanagement;

import com.service.parcelmanagement.domain.bookingmanagement.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingJpaRepository extends JpaRepository<Booking, Long> {
}
