package carsharingapp.app.dto;

import carsharingapp.app.enums.CarType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarRequestDto {
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Brand must not be blank")
    private String brand;

    @NotNull(message = "Type must be specified")
    private CarType type;

    @Min(value = 0, message = "Inventory must be zero or greater")
    private int inventory;

    @NotNull(message = "Daily fee is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily fee must be greater than 0")
    private BigDecimal dailyFee;
}
