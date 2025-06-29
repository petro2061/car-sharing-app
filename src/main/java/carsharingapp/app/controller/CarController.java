package carsharingapp.app.controller;

import carsharingapp.app.dto.car.CarRequestDto;
import carsharingapp.app.dto.car.CarResponseDto;
import carsharingapp.app.service.car.CarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CarResponseDto createCar(@RequestBody @Valid CarRequestDto carRequestDto) {
        return carService.save(carRequestDto);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    @GetMapping("/{id}")
    CarResponseDto getById(@PathVariable @Positive Long id) {
        return carService.getById(id);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    @GetMapping
    List<CarResponseDto> getAll(Pageable pageable) {
        return carService.getAll(pageable);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    CarResponseDto update(@PathVariable @Positive Long id,
                          @RequestBody @Valid CarRequestDto carRequestDto) {
        return carService.update(id, carRequestDto);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable @Positive Long id) {
        carService.delete(id);
    }
}
