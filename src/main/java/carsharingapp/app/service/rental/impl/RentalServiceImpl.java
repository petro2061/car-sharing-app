package carsharingapp.app.service.rental.impl;

import carsharingapp.app.dto.rental.RentalRequestDto;
import carsharingapp.app.dto.rental.RentalResponseDto;
import carsharingapp.app.dto.rental.RentalSearchParameters;
import carsharingapp.app.exception.EntityNotFoundException;
import carsharingapp.app.mapper.RentalMapper;
import carsharingapp.app.model.Car;
import carsharingapp.app.model.Rental;
import carsharingapp.app.model.User;
import carsharingapp.app.repository.SpecificationBuilder;
import carsharingapp.app.repository.car.CarRepository;
import carsharingapp.app.repository.rental.RentalRepository;
import carsharingapp.app.repository.user.UserRepository;
import carsharingapp.app.service.rental.RentalService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private static final Integer CAR_INVENTORY_VALUE = 1;
    private static final Integer MIN_CAR_INVENTORY = 0;

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final SpecificationBuilder<Rental> specificationBuilder;

    @Override
    public RentalResponseDto save(RentalRequestDto requestDto, Long userId) {
        Car car = findCarById(requestDto.getCarId());

        if (car.getInventory() <= MIN_CAR_INVENTORY) {
            throw new IllegalStateException("Car is not available for rent.");
        }

        User user = findUserById(userId);

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(user);
        rental.setRentalDate(requestDto.getRentalDate());
        rental.setReturnDate(requestDto.getReturnDate());
        rental.setActualReturnDate(null);
        rental.setActive(true);

        car.setInventory(car.getInventory() - CAR_INVENTORY_VALUE);
        carRepository.save(car);

        return rentalMapper.toResponseDto(rentalRepository.save(rental));
    }

    @Override
    public RentalResponseDto getRentalById(Long rentalId, Long userId, boolean isManager) {
        Rental rental = findRentalById(rentalId);

        if (!isManager && !rental.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have access to this rental");
        }
        return rentalMapper.toResponseDto(rental);
    }

    @Override
    public List<RentalResponseDto> getAllRentals(
            RentalSearchParameters searchParameters,
            Pageable pageable) {
        Specification<Rental> spec = specificationBuilder.build(searchParameters);

        return rentalRepository.findAll(spec).stream()
                .map(rentalMapper::toResponseDto)
                .toList();
    }

    @Override
    public RentalResponseDto returnRental(Long rentalId, Long userId, boolean isManager) {
        Rental rental = findRentalById(rentalId);

        if (!isManager && rental.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have access to this rental");
        }

        if (!rental.isActive() || rental.getActualReturnDate() != null) {
            throw new IllegalStateException("This rental has already been returned");
        }

        rental.setActualReturnDate(LocalDate.now());
        rental.setActive(false);

        Car car = rental.getCar();
        car.setInventory(car.getInventory() + CAR_INVENTORY_VALUE);
        carRepository.save(car);

        rentalRepository.save(rental);

        return rentalMapper.toResponseDto(rental);
    }

    private Car findCarById(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can not find car by id -> "
                                + carId));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can not find user by id -> "
                                + userId));
    }

    private Rental findRentalById(Long rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Rental not found with id: "
                                + rentalId));
    }
}
