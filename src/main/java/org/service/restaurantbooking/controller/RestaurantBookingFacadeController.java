package org.service.restaurantbooking.controller;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.service.RestaurantBookingFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/restaurant-booking-stats")
@RequiredArgsConstructor
public class RestaurantBookingFacadeController {

    private final RestaurantBookingFacade restaurantBookingFacade;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Map<String, Object>> getRestaurantWithBookingStats(@PathVariable Long restaurantId) {
        return restaurantBookingFacade.getRestaurantWithBookingStats(restaurantId)
                .map(stats -> new ResponseEntity<>(stats, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}