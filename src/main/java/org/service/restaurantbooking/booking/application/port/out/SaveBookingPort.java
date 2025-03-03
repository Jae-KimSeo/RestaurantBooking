package org.service.restaurantbooking.booking.application.port.out;

import org.service.restaurantbooking.booking.domain.Booking;

public interface SaveBookingPort {
    Long saveBooking(Booking booking);
    void updateBooking(Booking booking);
}