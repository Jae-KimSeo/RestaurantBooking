package org.service.restaurantbooking.booking.application.port.in;

import lombok.Value;
import org.service.restaurantbooking.booking.domain.Booking;

import java.time.LocalDateTime;

public interface CreateBookingUseCase {
    Long createBooking(CreateBookingCommand command);
    
    @Value
    class CreateBookingCommand {
        Long restaurantId;
        String customerName;
        String customerPhone;
        int partySize;
        LocalDateTime bookingTime;
    }
}