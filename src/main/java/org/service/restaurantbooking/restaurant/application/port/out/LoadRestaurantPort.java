package org.service.restaurantbooking.restaurant.application.port.out;

import org.service.restaurantbooking.restaurant.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface LoadRestaurantPort {
    List<Restaurant> loadAllRestaurants();
    Optional<Restaurant> loadRestaurantById(Long id);
    boolean existsById(Long id);
}