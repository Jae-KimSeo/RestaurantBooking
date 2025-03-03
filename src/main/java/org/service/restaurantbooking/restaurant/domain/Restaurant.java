package org.service.restaurantbooking.restaurant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private int capacity;
    private String cuisine;
    private String operatingHours;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 예약 관련 필드
    private boolean acceptsBookings;
    private int maxPartySize;
    private int minAdvanceBookingHours;
    private int maxAdvanceBookingDays;
    
    public void updateDetails(String name, String address, String phoneNumber, 
                              String cuisine, String operatingHours, int capacity) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cuisine = cuisine;
        this.operatingHours = operatingHours;
        this.capacity = capacity;
    }
    
    public void updateBookingSettings(boolean acceptsBookings, int maxPartySize, 
                                     int minAdvanceBookingHours, int maxAdvanceBookingDays) {
        this.acceptsBookings = acceptsBookings;
        this.maxPartySize = maxPartySize;
        this.minAdvanceBookingHours = minAdvanceBookingHours;
        this.maxAdvanceBookingDays = maxAdvanceBookingDays;
    }
}