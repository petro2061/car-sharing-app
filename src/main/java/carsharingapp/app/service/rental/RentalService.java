package carsharingapp.app.service.rental;

import carsharingapp.app.dto.rental.RentalRequestDto;
import carsharingapp.app.dto.rental.RentalResponseDto;
import carsharingapp.app.dto.rental.RentalSearchParameters;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RentalService {
    RentalResponseDto save(RentalRequestDto requestDto, Long userId);

    RentalResponseDto getRentalById(Long rentalId, Long userId, boolean isManager);

    List<RentalResponseDto> getAllRentals(
            RentalSearchParameters searchParameters,
            Pageable pageable);

    RentalResponseDto returnRental(Long rentalId, Long userId, boolean isManager);
}
