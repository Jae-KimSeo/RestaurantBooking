package org.service.restaurantbooking.booking.application.service;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.booking.application.port.in.CreateBookingUseCase;
import org.service.restaurantbooking.booking.application.port.in.GetBookingUseCase;
import org.service.restaurantbooking.booking.application.port.in.ManageBookingUseCase;
import org.service.restaurantbooking.booking.application.port.out.LoadBookingPort;
import org.service.restaurantbooking.booking.application.port.out.SaveBookingPort;
import org.service.restaurantbooking.booking.domain.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService implements CreateBookingUseCase, GetBookingUseCase, ManageBookingUseCase {

    private final LoadBookingPort loadBookingPort;
    private final SaveBookingPort saveBookingPort;

    @Override
    public Long createBooking(CreateBookingCommand command) {
        Booking booking = Booking.builder()
                .restaurantId(command.getRestaurantId())
                .customerName(command.getCustomerName())
                .customerPhone(command.getCustomerPhone())
                .partySize(command.getPartySize())
                .bookingTime(command.getBookingTime())
                .status(Booking.BookingStatus.PENDING)
                .build();
        
        return saveBookingPort.saveBooking(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> getBookingById(Long id) {
        return loadBookingPort.loadBooking(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByRestaurantId(Long restaurantId) {
        return loadBookingPort.loadBookingsByRestaurantId(restaurantId);
    }

    @Override
    public void confirmBooking(Long bookingId) {
        loadBookingPort.loadBooking(bookingId)
                .ifPresent(booking -> {
                    booking.confirm();
                    saveBookingPort.updateBooking(booking);
                });
    }

    @Override
    public void cancelBooking(Long bookingId) {
        loadBookingPort.loadBooking(bookingId)
                .ifPresent(booking -> {
                    booking.cancel();
                    saveBookingPort.updateBooking(booking);
                });
    }

    @Override
    public void completeBooking(Long bookingId) {
        loadBookingPort.loadBooking(bookingId)
                .ifPresent(booking -> {
                    booking.complete();
                    saveBookingPort.updateBooking(booking);
                });
    }
}