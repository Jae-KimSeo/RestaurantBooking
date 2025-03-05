package org.service.restaurantbooking.restaurant.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.restaurant.application.port.in.ManageTableUseCase;
import org.service.restaurantbooking.restaurant.domain.Table;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/tables")
@RequiredArgsConstructor
public class TableController {

    private final ManageTableUseCase manageTableUseCase;

    /**
     * 특정 레스토랑의 모든 테이블 조회
     */
    @GetMapping
    public ResponseEntity<List<TableResponse>> getAllTables(
            @PathVariable Long restaurantId
    ) {
        List<Table> tables = manageTableUseCase.getTablesByRestaurant(restaurantId);
        List<TableResponse> responseList = tables.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /**
     * 특정 레스토랑에 테이블 생성
     */
    @PostMapping
    public ResponseEntity<TableResponse> createTable(
            @PathVariable Long restaurantId,
            @RequestBody TableRequest request
    ) {
        ManageTableUseCase.CreateTableCommand command =
                new ManageTableUseCase.CreateTableCommand(
                        restaurantId,
                        request.getTableNumber(),
                        request.getCapacity(),
                        request.isAvailable()
                );

        Long tableId = manageTableUseCase.createTable(command);
        return manageTableUseCase.getTable(tableId)
                .map(table -> new ResponseEntity<>(toResponse(table), HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * 특정 테이블 상세 조회
     */
    @GetMapping("/{tableId}")
    public ResponseEntity<TableResponse> getTable(
            @PathVariable Long restaurantId,
            @PathVariable Long tableId
    ) {
        return manageTableUseCase.getTable(tableId)
                .map(table -> {
                    // 레스토랑 ID와 매칭되는지 확인 (옵션)
                    if (!table.getRestaurantId().equals(restaurantId)) {
                        return new ResponseEntity<TableResponse>(HttpStatus.NOT_FOUND);
                    }
                    return new ResponseEntity<TableResponse>(toResponse(table), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<TableResponse>(HttpStatus.NOT_FOUND));
    }

    /**
     * 특정 테이블 업데이트
     */
    @PutMapping("/{tableId}")
    public ResponseEntity<TableResponse> updateTable(
            @PathVariable Long restaurantId,
            @PathVariable Long tableId,
            @RequestBody TableRequest request
    ) {
        ManageTableUseCase.UpdateTableCommand command =
                new ManageTableUseCase.UpdateTableCommand(
                        tableId,
                        request.getTableNumber(),
                        request.getCapacity(),
                        request.isAvailable()
                );

        return manageTableUseCase.updateTable(command)
                .map(table -> {
                    if (!table.getRestaurantId().equals(restaurantId)) {
                        return new ResponseEntity<TableResponse>(HttpStatus.NOT_FOUND);
                    }
                    return new ResponseEntity<TableResponse>(toResponse(table), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<TableResponse>(HttpStatus.NOT_FOUND));
    }

    /**
     * 특정 테이블 삭제
     */
    @DeleteMapping("/{tableId}")
    public ResponseEntity<Void> deleteTable(
            @PathVariable Long restaurantId,
            @PathVariable Long tableId
    ) {
        // 존재 여부 확인
        if (!manageTableUseCase.getTable(tableId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean deleted = manageTableUseCase.deleteTable(tableId);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                       : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private TableResponse toResponse(Table table) {
        return new TableResponse(
                table.getId(),
                table.getRestaurantId(),
                table.getTableNumber(),
                table.getCapacity(),
                table.isAvailable()
        );
    }
}