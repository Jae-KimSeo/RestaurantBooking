package org.service.restaurantbooking.booking.application.port.in;

import org.service.restaurantbooking.booking.domain.Booking;

import java.util.List;
import java.util.Optional;

public interface GetBookingUseCase {
    Optional<Booking> getBookingById(Long id);
    List<Booking> getBookingsByRestaurantId(Long restaurantId);
}