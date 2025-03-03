package org.service.restaurantbooking.common.application.service;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.booking.application.port.in.GetBookingUseCase;
import org.service.restaurantbooking.booking.domain.Booking;
import org.service.restaurantbooking.restaurant.application.port.in.GetRestaurantUseCase;
import org.service.restaurantbooking.restaurant.domain.Restaurant;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Facade service that connects the hexagonal architecture services
 * and provides integration functionality between restaurant and booking domains
 */
@Service
@RequiredArgsConstructor
public class RestaurantBookingFacade {

    private final GetRestaurantUseCase getRestaurantUseCase;
    private final GetBookingUseCase getBookingUseCase;

    /**
     * Get restaurant details with booking statistics
     */
    public Optional<Map<String, Object>> getRestaurantWithBookingStats(Long restaurantId) {
        Optional<Restaurant> restaurantOpt = getRestaurantUseCase.getRestaurantById(restaurantId);
        
        if (restaurantOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Restaurant restaurant = restaurantOpt.get();
        List<Booking> bookings = getBookingUseCase.getBookingsByRestaurantId(restaurantId);
        
        Map<String, Object> result = new HashMap<>();
        
        // Restaurant info
        result.put("id", restaurant.getId());
        result.put("name", restaurant.getName());
        result.put("address", restaurant.getAddress());
        result.put("phoneNumber", restaurant.getPhoneNumber());
        result.put("capacity", restaurant.getCapacity());
        result.put("cuisine", restaurant.getCuisine());
        result.put("operatingHours", restaurant.getOperatingHours());
        
        // Booking settings
        result.put("acceptsBookings", restaurant.isAcceptsBookings());
        result.put("maxPartySize", restaurant.getMaxPartySize());
        result.put("minAdvanceBookingHours", restaurant.getMinAdvanceBookingHours());
        result.put("maxAdvanceBookingDays", restaurant.getMaxAdvanceBookingDays());
        
        // Booking statistics
        result.put("totalBookings", bookings.size());
        
        // Count bookings by status
        long pendingBookings = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.PENDING)
                .count();
        
        long confirmedBookings = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.CONFIRMED)
                .count();
        
        long canceledBookings = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.CANCELED)
                .count();
        
        long completedBookings = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.COMPLETED)
                .count();
        
        result.put("pendingBookings", pendingBookings);
        result.put("confirmedBookings", confirmedBookings);
        result.put("canceledBookings", canceledBookings);
        result.put("completedBookings", completedBookings);
        
        return Optional.of(result);
    }
}