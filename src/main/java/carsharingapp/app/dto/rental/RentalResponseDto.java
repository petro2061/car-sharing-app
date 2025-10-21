package carsharingapp.app.dto.rental;

import carsharingapp.app.dto.car.CarResponseDto;
import java.time.LocalDate;

public record RentalResponseDto(
        Long id,
        LocalDate rentalDate,
        LocalDate returnDate,
        LocalDate actualReturnDate,
        CarResponseDto carResponseDto
) {
}
