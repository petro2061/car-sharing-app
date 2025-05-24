package carsharingapp.app.service.car.impl;

import carsharingapp.app.dto.CarRequestDto;
import carsharingapp.app.dto.CarResponseDto;
import carsharingapp.app.exception.EntityNotFoundException;
import carsharingapp.app.mapper.CarMapper;
import carsharingapp.app.model.Car;
import carsharingapp.app.repository.CarRepository;
import carsharingapp.app.service.car.CarService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    @Transactional
    public CarResponseDto save(CarRequestDto carRequestDto) {
        Car car = carMapper.toCarModel(carRequestDto);
        return carMapper.toResponseDto(carRepository.save(car));
    }

    @Override
    public CarResponseDto getById(Long id) {
        return carMapper.toResponseDto(findCarById(id));
    }

    @Override
    public List<CarResponseDto> getAll(Pageable pageable) {
        return carRepository.findAll(pageable)
                .stream()
                .map(carMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public CarResponseDto update(Long id, CarRequestDto requestDto) {
        Car car = findCarById(id);
        carMapper.updateCarFromRequestDto(requestDto, car);
        return carMapper.toResponseDto(carRepository.save(car));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Car car = findCarById(id);
        carRepository.delete(car);
    }

    private Car findCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Car with id " + id + " not found"));
    }
}
