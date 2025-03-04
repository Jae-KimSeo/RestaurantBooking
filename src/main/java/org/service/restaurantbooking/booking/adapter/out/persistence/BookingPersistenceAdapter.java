package org.service.restaurantbooking.booking.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.booking.application.port.out.LoadBookingPort;
import org.service.restaurantbooking.booking.application.port.out.SaveBookingPort;
import org.service.restaurantbooking.booking.domain.Booking;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingPersistenceAdapter implements LoadBookingPort, SaveBookingPort {

    private final BookingRepository bookingRepository;

    @Override
    public Optional<Booking> loadBooking(Long id) {
        return bookingRepository.findById(id)
                .map(BookingJpaEntity::toDomain);
    }

    @Override
    public List<Booking> loadBookingsByRestaurantId(Long restaurantId) {
        return bookingRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(BookingJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Long saveBooking(Booking booking) {
        BookingJpaEntity savedEntity = bookingRepository.save(
                BookingJpaEntity.fromDomain(booking)
        );
        return savedEntity.getId();
    }

    @Override
    public void updateBooking(Booking booking) {
        bookingRepository.save(BookingJpaEntity.fromDomain(booking));
    }
}