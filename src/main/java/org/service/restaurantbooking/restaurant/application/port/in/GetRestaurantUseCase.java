package org.service.restaurantbooking.restaurant.application.port.in;

import org.service.restaurantbooking.restaurant.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface GetRestaurantUseCase {
    List<Restaurant> getAllRestaurants();
    Optional<Restaurant> getRestaurantById(Long id);
}