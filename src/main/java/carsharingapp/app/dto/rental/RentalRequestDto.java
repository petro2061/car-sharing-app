package carsharingapp.app.dto.rental;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalRequestDto {
    @NotNull
    private Long carId;
    @NotNull
    @FutureOrPresent
    private LocalDate rentalDate;
    @NotNull
    @Future
    private LocalDate returnDate;
}
