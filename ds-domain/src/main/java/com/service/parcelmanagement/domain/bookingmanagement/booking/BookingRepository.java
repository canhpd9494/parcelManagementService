package com.service.parcelmanagement.domain.bookingmanagement.booking;

public interface BookingRepository {
    void checkIn(Long bookingId);
    Booking createBooking(Integer guestId);
    void checkOut(Long bookingId);
}
