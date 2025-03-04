package org.service.restaurantbooking.booking.application.port.out;

import org.service.restaurantbooking.booking.domain.Booking;

import java.util.List;
import java.util.Optional;

public interface LoadBookingPort {
    Optional<Booking> loadBooking(Long id);
    List<Booking> loadBookingsByRestaurantId(Long restaurantId);
}