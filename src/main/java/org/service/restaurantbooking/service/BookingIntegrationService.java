package org.service.restaurantbooking.service;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.booking.application.port.in.CreateBookingUseCase;
import org.service.restaurantbooking.booking.application.port.in.GetBookingUseCase;
import org.service.restaurantbooking.booking.application.port.in.ManageBookingUseCase;
import org.service.restaurantbooking.restaurant.application.port.in.GetRestaurantUseCase;
import org.service.restaurantbooking.restaurant.domain.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingIntegrationService {

    private final GetRestaurantUseCase getRestaurantUseCase;
    private final CreateBookingUseCase createBookingUseCase;
    private final GetBookingUseCase getBookingUseCase;
    private final ManageBookingUseCase manageBookingUseCase;
    
    /**
     * 예약 가능 여부를 확인하고 예약을 생성합니다.
     */
    public Optional<Long> createBookingWithValidation(Long restaurantId, String customerName, 
                                                    String customerPhone, int partySize, 
                                                    LocalDateTime bookingTime) {
        
        // 1. 레스토랑 정보 확인
        Optional<Restaurant> restaurantOpt = getRestaurantUseCase.getRestaurantById(restaurantId);
        if (restaurantOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Restaurant restaurant = restaurantOpt.get();
        
        // 2. 예약 가능 여부 검증
        if (!validateBooking(restaurant, partySize, bookingTime)) {
            return Optional.empty();
        }
        
        // 3. 예약 생성
        CreateBookingUseCase.CreateBookingCommand command = 
                new CreateBookingUseCase.CreateBookingCommand(
                        restaurantId, 
                        customerName, 
                        customerPhone, 
                        partySize, 
                        bookingTime);
        
        Long bookingId = createBookingUseCase.createBooking(command);
        return Optional.of(bookingId);
    }
    
    /**
     * 예약 검증 로직
     */
    private boolean validateBooking(Restaurant restaurant, int partySize, LocalDateTime bookingTime) {
        // 1. 레스토랑이 예약을 받는지 확인
        if (!restaurant.isAcceptsBookings()) {
            return false;
        }
        
        // 2. 파티 크기 확인
        if (partySize <= 0 || partySize > restaurant.getMaxPartySize()) {
            return false;
        }
        
        // 3. 수용 인원 확인
        if (partySize > restaurant.getCapacity()) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        
        // 4. 최소 예약 시간 확인
        long hoursDifference = ChronoUnit.HOURS.between(now, bookingTime);
        if (hoursDifference < restaurant.getMinAdvanceBookingHours()) {
            return false;
        }
        
        // 5. 최대 예약 가능 일수 확인
        long daysDifference = ChronoUnit.DAYS.between(now, bookingTime);
        if (daysDifference > restaurant.getMaxAdvanceBookingDays()) {
            return false;
        }
        
        return true;
    }
}