package carsharingapp.app.dto;

import carsharingapp.app.enums.CarType;
import java.math.BigDecimal;

public record CarResponseDto(
        Long id,
        String name,
        String brand,
        CarType type,
        int inventory,
        BigDecimal dailyFee
) {
}
