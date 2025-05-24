package carsharingapp.app.controller;

import carsharingapp.app.dto.CarRequestDto;
import carsharingapp.app.dto.CarResponseDto;
import carsharingapp.app.service.car.CarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CarResponseDto createCar(@RequestBody @Valid CarRequestDto carRequestDto) {
        return carService.save(carRequestDto);
    }

    @GetMapping("/{id}")
    CarResponseDto getById(@PathVariable @Positive Long id) {
        return carService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CarResponseDto> getAll(Pageable pageable) {
        return carService.getAll(pageable);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CarResponseDto update(@PathVariable @Positive Long id,
                          @RequestBody @Valid CarRequestDto carRequestDto) {
        return carService.update(id, carRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable @Positive Long id) {
        carService.delete(id);
    }
}
