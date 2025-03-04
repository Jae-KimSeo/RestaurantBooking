package org.service.restaurantbooking.booking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private Long id;
    private Long restaurantId;
    private String customerName;
    private String customerPhone;
    private int partySize;
    private LocalDateTime bookingTime;
    private BookingStatus status;
    
    public enum BookingStatus {
        PENDING, 
        CONFIRMED, 
        CANCELED, 
        COMPLETED
    }
    
    public void confirm() {
        this.status = BookingStatus.CONFIRMED;
    }
    
    public void cancel() {
        this.status = BookingStatus.CANCELED;
    }
    
    public void complete() {
        this.status = BookingStatus.COMPLETED;
    }
}