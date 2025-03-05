package org.service.restaurantbooking.restaurant.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableTest {

    @Test
    @DisplayName("updateDetails: 테이블 번호 및 수용 인원 업데이트가 정상동작해야 한다.")
    void updateDetails() {
        // given
        Table table = Table.builder()
                .id(1L)
                .restaurantId(10L)
                .tableNumber(1)
                .capacity(4)
                .isAvailable(true)
                .build();

        // when
        table.updateDetails(2, 6);

        // then
        assertThat(table.getTableNumber()).isEqualTo(2);
        assertThat(table.getCapacity()).isEqualTo(6);
    }

    @Test
    @DisplayName("setAvailability: 테이블 가용성을 변경할 수 있어야 한다.")
    void setAvailability() {
        // given
        Table table = Table.builder()
                .id(1L)
                .restaurantId(10L)
                .tableNumber(1)
                .capacity(4)
                .isAvailable(true)
                .build();

        // when
        table.setAvailability(false);

        // then
        assertThat(table.isAvailable()).isFalse();
    }
}