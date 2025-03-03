package org.service.restaurantbooking.booking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.booking.application.port.in.CreateBookingUseCase;
import org.service.restaurantbooking.booking.application.port.in.GetBookingUseCase;
import org.service.restaurantbooking.booking.application.port.in.ManageBookingUseCase;
import org.service.restaurantbooking.booking.domain.Booking;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final CreateBookingUseCase createBookingUseCase;
    private final GetBookingUseCase getBookingUseCase;
    private final ManageBookingUseCase manageBookingUseCase;

    @PostMapping
    public ResponseEntity<Long> createBooking(@RequestBody BookingRequest request) {
        CreateBookingUseCase.CreateBookingCommand command = 
                new CreateBookingUseCase.CreateBookingCommand(
                request.getRestaurantId(),
                request.getCustomerName(),
                request.getCustomerPhone(),
                request.getPartySize(),
                request.getBookingTime()
        );
        
        Long bookingId = createBookingUseCase.createBooking(command);
        return new ResponseEntity<>(bookingId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        return getBookingUseCase.getBookingById(id)
                .map(booking -> new ResponseEntity<>(
                        mapToResponse(booking),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByRestaurantId(
            @PathVariable Long restaurantId) {
        List<BookingResponse> bookings = getBookingUseCase
                .getBookingsByRestaurantId(restaurantId)
                .stream()
                .map(this::mapToResponse)
                .toList();
        
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmBooking(@PathVariable Long id) {
        manageBookingUseCase.confirmBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        manageBookingUseCase.cancelBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeBooking(@PathVariable Long id) {
        manageBookingUseCase.completeBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

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