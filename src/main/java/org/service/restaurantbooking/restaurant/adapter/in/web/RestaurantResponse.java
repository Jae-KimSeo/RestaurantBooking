package org.service.restaurantbooking.restaurant.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private int capacity;
    private String cuisine;
    private String operatingHours;
    private boolean acceptsBookings;
    private int maxPartySize;
    private int minAdvanceBookingHours;
    private int maxAdvanceBookingDays;
}