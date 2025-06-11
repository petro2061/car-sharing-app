package carsharingapp.app.dto.car;

import carsharingapp.app.enums.CarType;
import java.math.BigDecimal;

public record CarResponseDto(
        Long id,
        String model,
        String brand,
        CarType type,
        int inventory,
        BigDecimal dailyFee
) {
}
