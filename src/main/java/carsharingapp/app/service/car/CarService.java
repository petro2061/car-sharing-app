package carsharingapp.app.service.car;

import carsharingapp.app.dto.CarRequestDto;
import carsharingapp.app.dto.CarResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CarService {
    CarResponseDto save(CarRequestDto carRequestDto);

    CarResponseDto getById(Long id);

    List<CarResponseDto> getAll(Pageable pageable);

    CarResponseDto update(Long id, CarRequestDto requestDto);

    void delete(Long id);
}
