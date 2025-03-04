package org.service.restaurantbooking.booking.application.port.in;

public interface ManageBookingUseCase {
    void confirmBooking(Long bookingId);
    void cancelBooking(Long bookingId);
    void completeBooking(Long bookingId);
}