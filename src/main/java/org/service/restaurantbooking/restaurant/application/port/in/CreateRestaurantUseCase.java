package org.service.restaurantbooking.restaurant.application.port.in;

import lombok.Value;
import org.service.restaurantbooking.restaurant.domain.Restaurant;

public interface CreateRestaurantUseCase {
    Long createRestaurant(CreateRestaurantCommand command);
    
    @Value
    class CreateRestaurantCommand {
        String name;
        String address;
        String phoneNumber;
        int capacity;
        String cuisine;
        String operatingHours;
        boolean acceptsBookings;
        int maxPartySize;
        int minAdvanceBookingHours;
        int maxAdvanceBookingDays;
    }
}