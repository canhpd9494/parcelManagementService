package com.service.parcelmanagement.infra.bookingmanagement;

import com.service.parcelmanagement.domain.bookingmanagement.booking.Booking;
import com.service.parcelmanagement.domain.bookingmanagement.booking.BookingRepository;
import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import com.service.parcelmanagement.infra.guestmanagement.GuestJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingJpaRepository jpaRepository;
    private final GuestJpaRepository guestJpaRepository;

    public BookingRepositoryImpl(BookingJpaRepository jpaRepository, GuestJpaRepository guestJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.guestJpaRepository = guestJpaRepository;
    }

    @Override
    public void checkIn(Long bookingId) {
        Optional<Booking> booking = jpaRepository.findById(bookingId);
        if (booking.isPresent()) {
            Booking updateBooking = booking.get();
            updateBooking.setCheckInDate(LocalDate.now());
            jpaRepository.save(updateBooking);
        }
    }

    @Override
    public Booking createBooking(Integer guestId) {
        Guest guest = guestJpaRepository.findById(guestId).get();
        Booking booking = new Booking();
        booking.setGuest(guest);
        return jpaRepository.save(booking);
    }

    @Override
    public void checkOut(Long bookingId) {
        Optional<Booking> booking = jpaRepository.findById(bookingId);
        if (booking.isPresent()) {
            Booking updateBooking = booking.get();
            updateBooking.setCheckOutDate(LocalDate.now());
            jpaRepository.save(updateBooking);
        }
    }
}
