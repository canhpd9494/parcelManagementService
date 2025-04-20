package com.service.parcelmanagement.domain.bookingmanagement;

import com.service.parcelmanagement.domain.bookingmanagement.booking.Booking;
import com.service.parcelmanagement.domain.bookingmanagement.booking.BookingRepository;
import com.service.parcelmanagement.domain.guestsmanagement.GuestRequestDomainService;
import org.springframework.stereotype.Service;

@Service
public class BookingRequestDomainService {
    private final BookingRepository bookingRepository;
    private final GuestRequestDomainService guestRequestDomainService;

    public BookingRequestDomainService(BookingRepository bookingRepository, GuestRequestDomainService guestRequestDomainService) {
        this.bookingRepository = bookingRepository;
        this.guestRequestDomainService = guestRequestDomainService;
    }

    public Booking createBooking(Integer guestId, Integer roomNumber) {
        return bookingRepository.createBooking(guestId);
    }

    public void checkIn(Long bookingId, Integer guestId, Integer roomNumber) {
        bookingRepository.checkIn(bookingId);
        guestRequestDomainService.checkIn(guestId, roomNumber);
    }

    public void checkOut(Long bookingId, Integer guestId) {
        bookingRepository.checkOut(bookingId);
        guestRequestDomainService.checkOut(guestId);
    }

}
