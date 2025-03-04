package org.service.restaurantbooking.restaurant.application.port.out;

import org.service.restaurantbooking.restaurant.domain.Restaurant;

public interface SaveRestaurantPort {
    Long saveRestaurant(Restaurant restaurant);
    void updateRestaurant(Restaurant restaurant);
}