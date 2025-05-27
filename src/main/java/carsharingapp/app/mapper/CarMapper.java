package carsharingapp.app.mapper;

import carsharingapp.app.config.MapperConfig;
import carsharingapp.app.dto.car.CarRequestDto;
import carsharingapp.app.dto.car.CarResponseDto;
import carsharingapp.app.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    CarResponseDto toResponseDto(Car car);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Car toCarModel(CarRequestDto carRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateCarFromRequestDto(CarRequestDto dto, @MappingTarget Car car);
}
