package org.service.restaurantbooking.restaurant.application.service;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.restaurant.application.port.in.CreateRestaurantUseCase;
import org.service.restaurantbooking.restaurant.application.port.in.DeleteRestaurantUseCase;
import org.service.restaurantbooking.restaurant.application.port.in.GetRestaurantUseCase;
import org.service.restaurantbooking.restaurant.application.port.in.UpdateRestaurantUseCase;
import org.service.restaurantbooking.restaurant.application.port.out.DeleteRestaurantPort;
import org.service.restaurantbooking.restaurant.application.port.out.LoadRestaurantPort;
import org.service.restaurantbooking.restaurant.application.port.out.SaveRestaurantPort;
import org.service.restaurantbooking.restaurant.domain.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService implements CreateRestaurantUseCase, GetRestaurantUseCase, 
                                         UpdateRestaurantUseCase, DeleteRestaurantUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final SaveRestaurantPort saveRestaurantPort;
    private final DeleteRestaurantPort deleteRestaurantPort;

    @Override
    public Long createRestaurant(CreateRestaurantCommand command) {
        Restaurant restaurant = Restaurant.builder()
                .name(command.getName())
                .address(command.getAddress())
                .phoneNumber(command.getPhoneNumber())
                .capacity(command.getCapacity())
                .cuisine(command.getCuisine())
                .operatingHours(command.getOperatingHours())
                .acceptsBookings(command.isAcceptsBookings())
                .maxPartySize(command.getMaxPartySize())
                .minAdvanceBookingHours(command.getMinAdvanceBookingHours())
                .maxAdvanceBookingDays(command.getMaxAdvanceBookingDays())
                .build();
        
        return saveRestaurantPort.saveRestaurant(restaurant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Restaurant> getAllRestaurants() {
        return loadRestaurantPort.loadAllRestaurants();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Restaurant> getRestaurantById(Long id) {
        return loadRestaurantPort.loadRestaurantById(id);
    }

    @Override
    public Optional<Restaurant> updateRestaurant(Long id, UpdateRestaurantCommand command) {
        return loadRestaurantPort.loadRestaurantById(id)
                .map(restaurant -> {
                    restaurant.updateDetails(
                            command.getName(),
                            command.getAddress(),
                            command.getPhoneNumber(),
                            command.getCuisine(),
                            command.getOperatingHours(),
                            command.getCapacity()
                    );
                    saveRestaurantPort.updateRestaurant(restaurant);
                    return restaurant;
                });
    }

    @Override
    public Optional<Restaurant> updateBookingSettings(Long id, UpdateBookingSettingsCommand command) {
        return loadRestaurantPort.loadRestaurantById(id)
                .map(restaurant -> {
                    restaurant.updateBookingSettings(
                            command.isAcceptsBookings(),
                            command.getMaxPartySize(),
                            command.getMinAdvanceBookingHours(),
                            command.getMaxAdvanceBookingDays()
                    );
                    saveRestaurantPort.updateRestaurant(restaurant);
                    return restaurant;
                });
    }

    @Override
    public boolean deleteRestaurant(Long id) {
        if (!loadRestaurantPort.existsById(id)) {
            return false;
        }
        deleteRestaurantPort.deleteRestaurant(id);
        return true;
    }
}