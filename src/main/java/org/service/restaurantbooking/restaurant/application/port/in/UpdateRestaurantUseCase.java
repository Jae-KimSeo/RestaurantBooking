package org.service.restaurantbooking.restaurant.application.port.in;

import lombok.Value;
import org.service.restaurantbooking.restaurant.domain.Restaurant;

import java.util.Optional;

public interface UpdateRestaurantUseCase {
    Optional<Restaurant> updateRestaurant(Long id, UpdateRestaurantCommand command);
    Optional<Restaurant> updateBookingSettings(Long id, UpdateBookingSettingsCommand command);
    
    @Value
    class UpdateRestaurantCommand {
        String name;
        String address;
        String phoneNumber;
        int capacity;
        String cuisine;
        String operatingHours;
    }
    
    @Value
    class UpdateBookingSettingsCommand {
        boolean acceptsBookings;
        int maxPartySize;
        int minAdvanceBookingHours;
        int maxAdvanceBookingDays;
    }
}