package carsharingapp.app.mapper;

import carsharingapp.app.config.MapperConfig;
import carsharingapp.app.dto.rental.RentalRequestDto;
import carsharingapp.app.dto.rental.RentalResponseDto;
import carsharingapp.app.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CarMapper.class)
public interface RentalMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "carResponseDto", ignore = true)
    RentalResponseDto toResponseDto(Rental rental);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actualReturnDate", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Rental toRentalModel(RentalRequestDto requestDto);
}
