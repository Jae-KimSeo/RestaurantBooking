package org.service.restaurantbooking.restaurant.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.restaurant.application.port.out.DeleteRestaurantPort;
import org.service.restaurantbooking.restaurant.application.port.out.LoadRestaurantPort;
import org.service.restaurantbooking.restaurant.application.port.out.SaveRestaurantPort;
import org.service.restaurantbooking.restaurant.domain.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestaurantPersistenceAdapter implements LoadRestaurantPort, SaveRestaurantPort, DeleteRestaurantPort {

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> loadAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(RestaurantJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Restaurant> loadRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(RestaurantJpaEntity::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return restaurantRepository.existsById(id);
    }

    @Override
    public Long saveRestaurant(Restaurant restaurant) {
        RestaurantJpaEntity savedEntity = restaurantRepository.save(
                RestaurantJpaEntity.fromDomain(restaurant)
        );
        return savedEntity.getId();
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        restaurantRepository.save(
                RestaurantJpaEntity.fromDomain(restaurant)
        );
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}