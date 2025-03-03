package org.service.restaurantbooking.restaurant.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingSettingsRequest {
    private boolean acceptsBookings;
    private int maxPartySize;
    private int minAdvanceBookingHours;
    private int maxAdvanceBookingDays;
}