package org.service.restaurantbooking.controller;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.booking.adapter.in.web.BookingRequest;
import org.service.restaurantbooking.booking.adapter.in.web.BookingResponse;
import org.service.restaurantbooking.booking.application.port.in.GetBookingUseCase;
import org.service.restaurantbooking.booking.domain.Booking;
import org.service.restaurantbooking.restaurant.application.port.in.GetRestaurantUseCase;
import org.service.restaurantbooking.service.BookingIntegrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurant-bookings")
@RequiredArgsConstructor
public class RestaurantBookingController {

    private final GetRestaurantUseCase getRestaurantUseCase;
    private final GetBookingUseCase getBookingUseCase;
    private final BookingIntegrationService bookingIntegrationService;

    // 특정 레스토랑의 모든 예약 조회
    @GetMapping("/restaurants/{restaurantId}/bookings")
    public ResponseEntity<List<BookingResponse>> getBookingsByRestaurantId(@PathVariable Long restaurantId) {
        var restaurant = getRestaurantUseCase.getRestaurantById(restaurantId);
        if (restaurant.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Booking> bookings = getBookingUseCase.getBookingsByRestaurantId(restaurantId);
        List<BookingResponse> responses = bookings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // 예약 생성 (유효성 검증 포함)
    @PostMapping("/restaurants/{restaurantId}/bookings")
    public ResponseEntity<Long> createBooking(
            @PathVariable Long restaurantId,
            @RequestBody BookingRequest request) {

        var bookingId = bookingIntegrationService.createBookingWithValidation(
                restaurantId,
                request.getCustomerName(),
                request.getCustomerPhone(),
                request.getPartySize(),
                request.getBookingTime()
        );

        return bookingId
                .map(id -> new ResponseEntity<>(id, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    // 예약 응답 매핑 
    private BookingResponse mapToResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getRestaurantId(),
                booking.getCustomerName(),
                booking.getCustomerPhone(),
                booking.getPartySize(),
                booking.getBookingTime(),
                booking.getStatus().name()
        );
    }
}