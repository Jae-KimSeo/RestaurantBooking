package org.service.restaurantbooking.restaurant.application.port.in;

import org.service.restaurantbooking.restaurant.domain.Table;

import java.util.List;
import java.util.Optional;

public interface ManageTableUseCase {

    /**
     * 새로운 테이블을 생성한다.
     */
    Long createTable(CreateTableCommand command);

    /**
     * 특정 레스토랑의 모든 테이블 목록을 조회한다.
     */
    List<Table> getTablesByRestaurant(Long restaurantId);

    /**
     * 특정 테이블을 ID로 조회한다.
     */
    Optional<Table> getTable(Long tableId);

    /**
     * 테이블 정보를 업데이트한다.
     */
    Optional<Table> updateTable(UpdateTableCommand command);

    /**
     * 테이블을 삭제한다.
     */
    boolean deleteTable(Long tableId);

    /**
     * 테이블 생성 시 필요한 정보
     */
    record CreateTableCommand(
            Long restaurantId,
            int tableNumber,
            int capacity,
            boolean isAvailable
    ) {}

    /**
     * 테이블 업데이트 시 필요한 정보
     */
    record UpdateTableCommand(
            Long tableId,
            int tableNumber,
            int capacity,
            boolean isAvailable
    ) {}
}