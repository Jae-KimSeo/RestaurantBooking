package org.service.restaurantbooking.restaurant.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.restaurant.application.port.in.CreateRestaurantUseCase;
import org.service.restaurantbooking.restaurant.application.port.in.DeleteRestaurantUseCase;
import org.service.restaurantbooking.restaurant.application.port.in.GetRestaurantUseCase;
import org.service.restaurantbooking.restaurant.application.port.in.UpdateRestaurantUseCase;
import org.service.restaurantbooking.restaurant.domain.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<Restaurant> restaurants = getRestaurantUseCase.getAllRestaurants();
        List<RestaurantResponse> response = restaurants.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        return getRestaurantUseCase.getRestaurantById(id)
                .map(restaurant -> new ResponseEntity<>(
                        mapToResponse(restaurant),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody RestaurantRequest request) {
        CreateRestaurantUseCase.CreateRestaurantCommand command = 
                new CreateRestaurantUseCase.CreateRestaurantCommand(
                        request.getName(),
                        request.getAddress(),
                        request.getPhoneNumber(),
                        request.getCapacity(),
                        request.getCuisine(),
                        request.getOperatingHours(),
                        request.isAcceptsBookings(),
                        request.getMaxPartySize(),
                        request.getMinAdvanceBookingHours(),
                        request.getMaxAdvanceBookingDays()
                );
        
        Long restaurantId = createRestaurantUseCase.createRestaurant(command);
        
        return getRestaurantUseCase.getRestaurantById(restaurantId)
                .map(restaurant -> new ResponseEntity<>(
                        mapToResponse(restaurant),
                        HttpStatus.CREATED
                ))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantRequest request) {
        
        UpdateRestaurantUseCase.UpdateRestaurantCommand command = 
                new UpdateRestaurantUseCase.UpdateRestaurantCommand(
                        request.getName(),
                        request.getAddress(),
                        request.getPhoneNumber(),
                        request.getCapacity(),
                        request.getCuisine(),
                        request.getOperatingHours()
                );
        
        return updateRestaurantUseCase.updateRestaurant(id, command)
                .map(restaurant -> new ResponseEntity<>(
                        mapToResponse(restaurant),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}/booking-settings")
    public ResponseEntity<RestaurantResponse> updateBookingSettings(
            @PathVariable Long id,
            @RequestBody BookingSettingsRequest request) {
        
        UpdateRestaurantUseCase.UpdateBookingSettingsCommand command = 
                new UpdateRestaurantUseCase.UpdateBookingSettingsCommand(
                        request.isAcceptsBookings(),
                        request.getMaxPartySize(),
                        request.getMinAdvanceBookingHours(),
                        request.getMaxAdvanceBookingDays()
                );
        
        return updateRestaurantUseCase.updateBookingSettings(id, command)
                .map(restaurant -> new ResponseEntity<>(
                        mapToResponse(restaurant),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        boolean isDeleted = deleteRestaurantUseCase.deleteRestaurant(id);
        return new ResponseEntity<>(isDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    private RestaurantResponse mapToResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber(),
                restaurant.getCapacity(),
                restaurant.getCuisine(),
                restaurant.getOperatingHours(),
                restaurant.isAcceptsBookings(),
                restaurant.getMaxPartySize(),
                restaurant.getMinAdvanceBookingHours(),
                restaurant.getMaxAdvanceBookingDays()
        );
    }
}