package org.service.restaurantbooking.booking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long restaurantId;
    private String customerName;
    private String customerPhone;
    private int partySize;
    private LocalDateTime bookingTime;
    private String status;
}