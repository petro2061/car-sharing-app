package carsharingapp.app.repository.rental;

import carsharingapp.app.dto.rental.RentalSearchParameters;
import carsharingapp.app.model.Rental;
import carsharingapp.app.repository.SpecificationBuilder;
import carsharingapp.app.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalSpecificationBuilder implements SpecificationBuilder<Rental> {
    private static final String USER_ID_KEY = "userIds";
    private static final String IS_ACTIVE_KEY = "isActive";

    private final SpecificationProviderManager<Rental> specificationProviderManager;

    @Override
    public Specification<Rental> build(RentalSearchParameters searchParameters) {
        Specification<Rental> spec = Specification.where(null);

        if (searchParameters.getUserIds() != null && !searchParameters.getUserIds().isEmpty()) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider(USER_ID_KEY)
                    .getSpecification(searchParameters.getUserIds()
                            .stream()
                            .map(String::valueOf)
                            .toList()));
        }

        if (searchParameters.getIsActive() != null) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider(IS_ACTIVE_KEY)
                    .getSpecification(searchParameters.getIsActive()
                            .stream()
                            .map(String::valueOf)
                            .toList()));
        }
        return spec;
    }
}
