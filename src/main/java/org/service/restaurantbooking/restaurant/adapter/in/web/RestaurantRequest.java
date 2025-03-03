package org.service.restaurantbooking.restaurant.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {
    private String name;
    private String address;
    private String phoneNumber;
    private int capacity;
    private String cuisine;
    private String operatingHours;
    private boolean acceptsBookings = true;
    private int maxPartySize = 10;
    private int minAdvanceBookingHours = 1;
    private int maxAdvanceBookingDays = 30;
}