package org.service.restaurantbooking.booking.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.service.restaurantbooking.booking.domain.Booking;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;
    
    @Column(name = "party_size", nullable = false)
    private int partySize;
    
    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;
    
    public enum BookingStatus {
        PENDING, 
        CONFIRMED, 
        CANCELED, 
        COMPLETED
    }
    
    public static BookingJpaEntity fromDomain(Booking booking) {
        return BookingJpaEntity.builder()
                .id(booking.getId())
                .restaurantId(booking.getRestaurantId())
                .customerName(booking.getCustomerName())
                .customerPhone(booking.getCustomerPhone())
                .partySize(booking.getPartySize())
                .bookingTime(booking.getBookingTime())
                .status(BookingStatus.valueOf(booking.getStatus().name()))
                .build();
    }
    
    public Booking toDomain() {
        return Booking.builder()
                .id(id)
                .restaurantId(restaurantId)
                .customerName(customerName)
                .customerPhone(customerPhone)
                .partySize(partySize)
                .bookingTime(bookingTime)
                .status(Booking.BookingStatus.valueOf(status.name()))
                .build();
    }
}